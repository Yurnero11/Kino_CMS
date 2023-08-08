package com.example.Kino_CMS.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {
    @Autowired
    private JavaMailSender javaMailSender;

    private final String uploadDir = "upload";

    /*public void sendEmailToUser(String email, String subject, String content, MultipartFile file) {
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
    }*/
}