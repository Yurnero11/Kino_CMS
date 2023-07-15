package com.example.Kino_CMS.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    private final String uploadDir = "upload";

    public void sendEmailToUser(String email, String subject, String content, MultipartFile file) {
        // Сгенерировать уникальное имя файла
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        // Определить путь сохранения файла
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Path filePath = uploadPath.resolve(fileName);

        try {
            // Сохранить файл на сервере
            Files.createDirectories(uploadPath);
            file.transferTo(filePath.toFile());
        } catch (IOException e) {
            e.printStackTrace();
            // Обработка ошибки сохранения файла
        }

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(content);

            if (file != null && !file.isEmpty()) {
                // Получение имени и содержимого файла
                String fileName1 = file.getOriginalFilename();
                byte[] fileContent = file.getBytes();

                // Вложение файла в письмо
                helper.addAttachment(fileName1, new ByteArrayResource(fileContent));
            }

            javaMailSender.send(message);
        } catch (MessagingException | IOException e) {
            // Обработка ошибки отправки письма
            e.printStackTrace();
        }
    }
}

