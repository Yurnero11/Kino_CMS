package com.example.Kino_CMS.controller;

import com.example.Kino_CMS.entity.EmailRequest;
import com.example.Kino_CMS.entity.EmailResponse;
import com.example.Kino_CMS.entity.FileUploads;
import com.example.Kino_CMS.entity.User;
import com.example.Kino_CMS.repository.FileUploadsRepository;
import com.example.Kino_CMS.service.EmailService;
import com.example.Kino_CMS.service.UserService;
import com.example.Kino_CMS.service.impl.FileUploadsServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
public class EmailController {
    private final FileUploadsRepository fileUploadsRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileUploadsServiceImpl fileUploadsService;

    @Autowired
    private JavaMailSender emailSender;

    @GetMapping("/file-delete/{fileId}")
    public String confirmDeleteFile(@PathVariable("fileId") Long fileId, Model model) {
        // Подтверждение удаления файла
        model.addAttribute("fileId", fileId);
        return "confirm-delete"; // Ваш представление с подтверждением удаления файла
    }

    @PostMapping("/file-delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") Long fileId) {
        try {
            fileUploadsService.deleteFile(fileId);
            return "redirect:/email-users";
        } catch (Exception e) {
            // Обработка ошибки удаления файла
            return "redirect:/email-users?error";
        }
    }

    public EmailController(FileUploadsRepository fileUploadsRepository) {
        this.fileUploadsRepository = fileUploadsRepository;
    }

    @GetMapping("/email-users")
    public String emailUsers(Model model) {
        Iterable<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "mail-sender/email-users"; // Исправленный путь к представлению
    }

    @GetMapping("/email-form")
    public String showEmailForm(Model model) {
        // Получить список пользователей из базы данных или другого источника данных
        Iterable<User> userIterable = userService.getAllUsers();

        // Получить список файлов из базы данных
        Iterable<FileUploads> fileIterable = fileUploadsService.getAllFiles();

        // Преобразование Iterable в List
        List<User> users = new ArrayList<>();
        userIterable.forEach(users::add);

        int limit = 5; // Ваше значение лимита

        List<FileUploads> fileList = new ArrayList<>();
        fileIterable.forEach(fileList::add);
        Collections.reverse(fileList); // Обратить порядок списка файлов

        boolean isDisabled = users.isEmpty(); // Определяет, должен ли быть список пользователей неактивным

        model.addAttribute("users", users);
        model.addAttribute("fileList", fileList); // Добавляем список файлов в модель
        model.addAttribute("limit", limit);
        model.addAttribute("disabled", isDisabled);

        // Инициализация поля fileIds
        model.addAttribute("fileIds", new ArrayList<Long>());


        return "/mail-sender/mail-sender";
    }

    @PostMapping("/send-email")
    @ResponseBody
    public ResponseEntity<EmailResponse> sendEmail(@ModelAttribute EmailRequest request, @RequestParam("file") MultipartFile file, @RequestParam(value = "selectedFile", required = false) Long selectedFileId, @RequestParam(value = "recipients", required = false) List<String> recipients) {
        if (recipients == null || recipients.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new EmailResponse("No recipients selected", 0));
        }

        String filePath;
        try {
            if (selectedFileId != null) {
                // Получить информацию о выбранном файле из базы данных
                FileUploads fileUploads = fileUploadsService.getFileById(selectedFileId);
                if (fileUploads == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new EmailResponse("File not found", 0));
                }
                filePath = fileUploads.getFile_path();

                // TODO: Добавьте соответствующий код для получения оригинального имени файла из объекта fileUploads
                String originalFileName = fileUploads.getOriginalFileName();

                for (String recipient : recipients) {
                    try {
                        // Отправить письмо с вложением и ссылкой на файл
                        sendHtmlEmailWithAttachment(recipient, request.getSubject(), request.getContent(), filePath, originalFileName);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EmailResponse("Failed to send email", 0));
                    }
                }
            } else if (!file.isEmpty()) {
                // Сохранить загруженный файл только один раз
                filePath = saveFile(file);

                // Создать объект FileUploads и сохранить его в базе данных только один раз
                FileUploads fileUploads = new FileUploads();
                fileUploads.setFile_path(filePath);
                fileUploads.setOriginalFileName(file.getOriginalFilename());
                fileUploadsRepository.save(fileUploads);

                for (String recipient : recipients) {
                    try {
                        // Отправить письмо с вложением и ссылкой на файл
                        sendHtmlEmailWithAttachment(recipient, request.getSubject(), request.getContent(), filePath, file.getOriginalFilename());
                    } catch (MessagingException e) {
                        e.printStackTrace();
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EmailResponse("Failed to send email", 0));
                    }
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new EmailResponse("No file selected", 0));
            }

            int sentCount = recipients.size(); // Получить количество отправленных писем
            EmailResponse emailResponse = new EmailResponse("Email sent successfully", sentCount);
            return ResponseEntity.ok(emailResponse);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EmailResponse("Failed to process file", 0));
        }
    }

    @PostMapping("/send-email/all")
    @ResponseBody
    public ResponseEntity<EmailResponse> sendEmailToAll(@ModelAttribute EmailRequest request, @RequestParam("file") MultipartFile file, @RequestParam(value = "recipients", required = false) List<String> recipients) {
        if (recipients == null) {
            recipients = new ArrayList<>(); // Если нет выбранных получателей, создаем пустой список
        }

        String filePath;
        try {
            if (!file.isEmpty()) {
                // Сохранить загруженный файл только один раз
                filePath = saveFile(file);

                // Создать объект FileUploads и сохранить его в базе данных только один раз
                FileUploads fileUploads = new FileUploads();
                fileUploads.setFile_path(filePath);
                fileUploads.setOriginalFileName(file.getOriginalFilename());
                fileUploadsRepository.save(fileUploads);
            } else {
                // Файл не загружен, получить информацию о файле из базы данных
                FileUploads fileUploads = fileUploadsService.getFileByOriginalFileName(file.getOriginalFilename());
                if (fileUploads == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new EmailResponse("File not found", 0));
                }
                filePath = fileUploads.getFile_path();
            }

            if (recipients.isEmpty()) {
                // Получить список всех пользователей
                List<String> allUserEmails = getEmailsOfAllUsers();
                recipients.addAll(allUserEmails); // Добавить всех пользователей в список получателей
            }

            if (recipients.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new EmailResponse("No recipients found", 0));
            }

            for (String recipient : recipients) {
                try {
                    // Отправить письмо с вложением и ссылкой на файл
                    sendHtmlEmailWithAttachment(recipient, request.getSubject(), request.getContent(), filePath, file.getOriginalFilename());
                } catch (MessagingException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EmailResponse("Failed to send email", 0));
                }
            }

            int sentCount = recipients.size(); // Получить количество отправленных писем
            EmailResponse emailResponse = new EmailResponse("Email sent to all users successfully", sentCount);
            return ResponseEntity.ok(emailResponse);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EmailResponse("Failed to save file", 0));
        }
    }

    private List<String> getEmailsOfAllUsers() {
        List<String> emails = new ArrayList<>();
        Iterable<User> users = userService.getAllUsers();
        for (User user : users) {
            emails.add(user.getEmail());
        }
        return emails;
    }

    private final String uploadDir = "upload/email_upload";

    private String saveFile(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uniqueFileName = generateUniqueFileName(originalFileName);
            String filePath = uploadDir + "/" + uniqueFileName;

            // Проверяем, существует ли файл с таким путем
            if (!Files.exists(Paths.get(filePath))) {
                try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
                }
            }

            return filePath; // Возвращаем только путь к файлу, без сохранения в базе данных
        } else {
            return null;
        }
    }

    private String generateUniqueFileName(String originalFileName) {
        String timeStamp = UUID.randomUUID().toString();
        return timeStamp + "_" + originalFileName;
    }

    private void sendHtmlEmailWithAttachment(String recipientEmail, String subject, String content, String filePath, String originalFileName) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(recipientEmail);
        helper.setSubject(subject);
        helper.setText(content, true);

        FileSystemResource fileResource = new FileSystemResource(filePath);
        helper.addAttachment(originalFileName, fileResource);

        emailSender.send(message);
    }

    private boolean checkFileExistsInDB(String originalFileName) {
        // Выполнить запрос в базу данных для проверки наличия файла по его имени
        FileUploads file = fileUploadsRepository.findByOriginalFileName(originalFileName);

        // Если файл найден, возвращаем true, иначе false
        return file != null;
    }
}