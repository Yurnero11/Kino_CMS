package com.example.Kino_CMS.controller.adminController;

import com.example.Kino_CMS.entity.*;
import com.example.Kino_CMS.repository.EmailCountRepository;
import com.example.Kino_CMS.repository.FileUploadsRepository;
import com.example.Kino_CMS.repository.UserRepository;
import com.example.Kino_CMS.service.impl.UserServiceImpl;
import com.example.Kino_CMS.service.impl.FileUploadsServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
    private final int pageSize = 6;

    @Autowired
    private FileUploadsRepository fileUploadsRepository;
    @Autowired
    private EmailCountRepository emailCountRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileUploadsServiceImpl fileUploadsService;

    @Autowired
    private JavaMailSender emailSender;


    @PostMapping("/file-delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") Long fileId) {
        try {
            // Получите информацию о файле по его ID
            FileUpload fileUpload = fileUploadsService.getFileById(fileId);
            if (fileUpload != null) {
                // Удалите файл из папки
                deleteFileFromFolder(fileUpload.getOriginalFileName());

                // Удалите запись о файле из базы данных
                fileUploadsService.deleteFile(fileId);
            }

            return "redirect:/email-users";
        } catch (Exception e) {
            // Обработка ошибки удаления файла
            return "redirect:/email-users?error";
        }
    }

    private void deleteFileFromFolder(String originalFileName) {
        String uploadPath = "upload/email_upload";
        Path filePath = Paths.get(uploadPath, originalFileName);

        try {
            Files.delete(filePath);
        } catch (IOException e) {
            // Handle the exception, e.g., log an error
            e.printStackTrace();
        }
    }

    public EmailController(FileUploadsRepository fileUploadsRepository, EmailCountRepository emailCountRepository) {
        this.fileUploadsRepository = fileUploadsRepository;
        this.emailCountRepository = emailCountRepository;
    }

    @GetMapping("/admin/select-recipients")
    public String showRecipientSelectionPage(Model model) {
        Iterable<User> users = userServiceImpl.getAllUsers(); // Здесь предполагается, что у вас есть метод для получения всех пользователей
        model.addAttribute("users", users);
        return "/admin/mail-sender/email-users"; // Вернуть имя шаблона Thymeleaf для страницы выбора получателей
    }

    @GetMapping("/admin/email-users")
    public String emailUsers(@RequestParam(name = "page", defaultValue = "0") int page,
                             @RequestParam(name = "query", required = false) String query,
                             Model model,
                             Authentication authentication) {

        int pageSize = 6;
        Page<User> userPage = userServiceImpl.findAllPage(page, pageSize);
        List<User> users = userPage.getContent();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String currentUserEmail = userDetails.getUsername();

        User currentUser = userRepository.findByEmail(currentUserEmail); // Здесь предполагается, что у вас есть метод для поиска пользователя по email

        model.addAttribute("currentUser", currentUser);

        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());

        int nextPage = page + 1;
        int totalPages = userPage.getTotalPages();
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("totalPages", totalPages);

        long totalUsers = userServiceImpl.countTotalUsers();
        boolean showPagination = totalUsers > 6;
        model.addAttribute("showPagination", showPagination);

        return "admin/mail-sender/email-users"; // Исправленный путь к представлению
    }

    @PostMapping("/admin/email-users")
    public String nextPageUsers(@RequestParam("page") int page) {
        return "redirect:/admin/email-users?page=" + page;
    }

    @GetMapping("/admin/email-form")
    public String showEmailForm(Model model) {
        // Получить список пользователей из базы данных или другого источника данных
        Iterable<User> userIterable = userServiceImpl.getAllUsers();

        // Получить список файлов из базы данных
        Iterable<FileUpload> fileIterable = fileUploadsService.getAllFiles();

        // Преобразование Iterable в List
        List<User> users = new ArrayList<>();
        userIterable.forEach(users::add);

        int limit = 5; // Ваше значение лимита

        List<FileUpload> fileList = new ArrayList<>();
        fileIterable.forEach(fileList::add);
        Collections.reverse(fileList); // Обратить порядок списка файлов

        boolean isDisabled = users.isEmpty(); // Определяет, должен ли быть список пользователей неактивным


        model.addAttribute("users", users);
        model.addAttribute("fileList", fileList); // Добавляем список файлов в модель
        model.addAttribute("limit", limit);
        model.addAttribute("disabled", isDisabled);
        model.addAttribute("sentCount", emailCountRepository.getSentCount());

        // Инициализация поля fileIds
        model.addAttribute("fileIds", new ArrayList<Long>());


        return "admin/mail-sender/mail-sender";
    }

    @GetMapping("/admin/email-sent-count")
    @ResponseBody
    public ResponseEntity<Integer> getEmailSentCount() {
        int sentCount = emailCountRepository.getSentCount(); // Замените getSentCount() на ваш метод для получения значения из базы данных

        return ResponseEntity.ok(sentCount);
    }

    @PostMapping("/admin/send-email")
    @ResponseBody
    public ResponseEntity<EmailResponse> sendEmail(@ModelAttribute EmailRequest request, @RequestParam("file") MultipartFile file, @RequestParam(value = "selectedFile", required = false) Long selectedFileId, @RequestParam(value = "recipients", required = false) List<String> recipients) {
        if (recipients == null || recipients.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new EmailResponse("No recipients selected", 0));
        }

        String filePath;
        try {
            if (selectedFileId != null) {
                // Получить информацию о выбранном файле из базы данных
                FileUpload fileUpload = fileUploadsService.getFileById(selectedFileId);
                if (fileUpload == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new EmailResponse("File not found", 0));
                }
                filePath = fileUpload.getFile_path();

                // TODO: Добавьте соответствующий код для получения оригинального имени файла из объекта fileUploads
                String originalFileName = fileUpload.getOriginalFileName();

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
                FileUpload fileUpload = new FileUpload();
                fileUpload.setFile_path(filePath);
                fileUpload.setOriginalFileName(file.getOriginalFilename());
                fileUploadsRepository.save(fileUpload);

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

            // Получение текущего значения общего количества отправленных писем
            Count count = emailCountRepository.findById(1L).orElse(new Count(0));
            count.setCount(count.getCount() + sentCount);

            // Сохранение обновленного значения общего количества в базе данных
            emailCountRepository.save(count);

            EmailResponse emailResponse = new EmailResponse("Email sent successfully", sentCount);
            return ResponseEntity.ok(emailResponse);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EmailResponse("Failed to process file", 0));
        }
    }

    @PostMapping("/admin/send-email/all")
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
                FileUpload fileUpload = new FileUpload();
                fileUpload.setFile_path(filePath);
                fileUpload.setOriginalFileName(file.getOriginalFilename());
                fileUploadsRepository.save(fileUpload);
            } else {
                // Файл не загружен, получить информацию о файле из базы данных
                FileUpload fileUpload = fileUploadsService.getFileByOriginalFileName(file.getOriginalFilename());
                if (fileUpload == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new EmailResponse("File not found", 0));
                }
                filePath = fileUpload.getFile_path();
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

            // Получение текущего значения общего количества отправленных писем
            Count count = emailCountRepository.findById(1L).orElse(new Count(0));
            count.setCount(count.getCount() + sentCount);

            // Сохранение обновленного значения общего количества в базе данных
            emailCountRepository.save(count);

            EmailResponse emailResponse = new EmailResponse("Email sent successfully", sentCount);
            return ResponseEntity.ok(emailResponse);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EmailResponse("Failed to save file", 0));
        }
    }

    private List<String> getEmailsOfAllUsers() {
        List<String> emails = new ArrayList<>();
        Iterable<User> users = userServiceImpl.getAllUsers();
        for (User user : users) {
            emails.add(user.getEmail());
        }
        return emails;
    }

    @Value("${spring.pathFiles}")
    String pathFiles;

    //private final String uploadDir = "upload/email_upload";

    private String saveFile(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uniqueFileName = generateUniqueFileName(originalFileName);
            String filePath = pathFiles + "/" + uniqueFileName;

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

        // Установите содержимое письма как HTML-файл
        helper.setText(content, true);

        // Прочитайте содержимое HTML-файла
        String htmlContent = readHtmlFile(filePath);

        // Установите содержимое HTML-файла как тело письма
        helper.setText(htmlContent, true);

        emailSender.send(message);
    }

    private String readHtmlFile(String filePath) {
        try {
            byte[] contentBytes = Files.readAllBytes(Paths.get(filePath));
            return new String(contentBytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private boolean checkFileExistsInDB(String originalFileName) {
        // Выполнить запрос в базу данных для проверки наличия файла по его имени
        FileUpload file = fileUploadsRepository.findByOriginalFileName(originalFileName);

        // Если файл найден, возвращаем true, иначе false
        return file != null;
    }
}