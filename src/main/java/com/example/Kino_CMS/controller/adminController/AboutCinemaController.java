package com.example.Kino_CMS.controller.adminController;

import com.example.Kino_CMS.entity.AboutCinema;
import com.example.Kino_CMS.entity.MainPage;
import com.example.Kino_CMS.repository.AboutCinemaRepository;
import com.example.Kino_CMS.service.impl.AboutCinemaServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Controller
public class AboutCinemaController {
    private final AboutCinemaRepository aboutCinemaRepository;
    private final AboutCinemaServiceImpl aboutCinemaServiceImpl;

    public AboutCinemaController(AboutCinemaRepository aboutCinemaRepository, AboutCinemaServiceImpl aboutCinemaServiceImpl) {
        this.aboutCinemaRepository = aboutCinemaRepository;
        this.aboutCinemaServiceImpl = aboutCinemaServiceImpl;
    }

    @GetMapping("/admin/pages/about-cinema")
    public String aboutCinema(Model model){
        AboutCinema aboutCinema = aboutCinemaServiceImpl.getAboutCinemaById(1L); // Если запись с id=2 не найдена, то вернем пустой объект MainPage
        model.addAttribute("about_cinema", aboutCinema);
        return "admin/pages/about-cinema";
    }

    @PostMapping("/admin/pages/about-cinema")
    public String cinemaPageEdit(@ModelAttribute("about_cinema") MainPage mainPage,
                                 @RequestParam("name") String cinema_name,
                                 @RequestParam("description") String description,
                                 @RequestParam("status") String status,
                                 @RequestParam("mainImageFile") MultipartFile mainImageFile,
                                 @RequestParam("gallery_photo_1") MultipartFile imageFile1,
                                 @RequestParam("gallery_photo_1") MultipartFile imageFile2,
                                 @RequestParam("gallery_photo_1") MultipartFile imageFile3,
                                 @RequestParam("gallery_photo_1") MultipartFile imageFile4,
                                 @RequestParam("gallery_photo_1") MultipartFile imageFile5,
                                 @RequestParam("url") String url,
                                 @RequestParam("title") String title,
                                 @RequestParam("keywords") String keywords,
                                 @RequestParam("description_seo") String descriptionSeo,
                                 RedirectAttributes redirectAttributes) {

        // Здесь предполагается, что объект MainPage уже содержит данные из формы,
        // переданные через th:object в представлении.

        // Выполните поиск существующего объекта MainPage по его id в базе данных.
        long id = 1L;

        AboutCinema existingMainPage = aboutCinemaServiceImpl.getAboutCinemaById(id);

        if (existingMainPage!= null) {

            // Обновите поля существующего объекта MainPage данными из формы.
            existingMainPage.setCinema_name(cinema_name);
            existingMainPage.setDescription(description);
            existingMainPage.setStatus(status);
            existingMainPage.setSeo_url(url);
            existingMainPage.setSeo_keywords(keywords);
            existingMainPage.setSeo_title(title);
            existingMainPage.setSeo_description(descriptionSeo);

            // Обновление главного изображения, если выбран новый файл
            String mainImagePath = saveImage(mainImageFile, existingMainPage.getMain_image_path());
            if (mainImagePath != null) {
                existingMainPage.setMain_image_path(mainImagePath);
            }

            // Обновление image_path_1, если выбран новый файл
            String imagePath1 = saveImage(imageFile1, existingMainPage.getImage_path_1());
            if (imagePath1 != null) {
                existingMainPage.setImage_path_1(imagePath1);
            }

            // Обновление image_path_1, если выбран новый файл
            String imagePath2 = saveImage(imageFile2, existingMainPage.getImage_path_2());
            if (imagePath2 != null) {
                existingMainPage.setImage_path_2(imagePath2);
            }

            // Обновление image_path_1, если выбран новый файл
            String imagePath3 = saveImage(imageFile3, existingMainPage.getImage_path_3());
            if (imagePath3 != null) {
                existingMainPage.setImage_path_3(imagePath3);
            }

            // Обновление image_path_1, если выбран новый файл
            String imagePath4 = saveImage(imageFile4, existingMainPage.getImage_path_4());
            if (imagePath1 != null) {
                existingMainPage.setImage_path_4(imagePath4);
            }

            // Обновление image_path_1, если выбран новый файл
            String imagePath5 = saveImage(imageFile5, existingMainPage.getImage_path_5());
            if (imagePath5 != null) {
                existingMainPage.setImage_path_5(imagePath5);
            }

            // Повторите для image_path_2, image_path_3, image_path_4 и image_path_5

            // Сохраните обновленный объект MainPage в базе данных.
            aboutCinemaServiceImpl.saveAboutCinema(existingMainPage);

            // Вы можете добавить атрибуты Flash для отображения сообщений об успешном обновлении, если хотите.
            redirectAttributes.addFlashAttribute("successMessage", "Данные успешно обновлены.");
        } else {
            // Если объект MainPage с заданным id не найден, выполните соответствующие действия.
            // Например, можно создать новый объект и сохранить его, если это приемлемо.
            // Или вы можете выбрать другую логику обработки в этом случае.
        }

        return "redirect:/admin/pages";
    }

    private final String uploadDir = "upload";

    private String saveImage(MultipartFile file, String currentImagePath) {
        if (file != null && !file.isEmpty()) {
            try {
                String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
                String fileExtension = getFileExtension(originalFileName);
                String uniqueFileName = generateUniqueFileName(fileExtension);

                Path filePath = Paths.get(uploadDir, uniqueFileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                return uniqueFileName;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return currentImagePath; // Возвращаем старое значение
        }
    }

    private String generateUniqueFileName(String fileExtension) {
        String timeStamp = UUID.randomUUID().toString();
        return timeStamp + "." + fileExtension;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        } else {
            return "";
        }
    }
}
