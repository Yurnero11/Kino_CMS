package com.example.Kino_CMS.controller.adminController;

import com.example.Kino_CMS.entity.Advertising;
import com.example.Kino_CMS.entity.CafeBar;
import com.example.Kino_CMS.entity.VipHall;
import com.example.Kino_CMS.repository.AdvertisingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class AdvertisingController {
    @Autowired
    private AdvertisingRepository advertisingRepository;

    @GetMapping("/admin/pages/advertising-page")
    public String advertisingPage(Model model){
        Advertising advertising = advertisingRepository.findById(1L).orElse(new Advertising());
        model.addAttribute("advertising", advertising);
        return "admin/pages/advertising-page";
    }

    @PostMapping("/admin/pages/advertising-page")
    public String advertisingPage(@ModelAttribute("advertising") Advertising advertising,
                                 @RequestParam("name") String advertising_name,
                                 @RequestParam("description") String description,
                                  @RequestParam(value = "status", required = false, defaultValue = "off") String status,
                                 @RequestParam("mainImageFile") MultipartFile mainImageFile,
                                 @RequestParam("gallery_photo_1") MultipartFile imageFile1,
                                 @RequestParam("gallery_photo_2") MultipartFile imageFile2,
                                 @RequestParam("gallery_photo_3") MultipartFile imageFile3,
                                 @RequestParam("gallery_photo_4") MultipartFile imageFile4,
                                 @RequestParam("gallery_photo_5") MultipartFile imageFile5,
                                 @RequestParam("url") String url,
                                 @RequestParam("title") String title,
                                 @RequestParam("keywords") String keywords,
                                 @RequestParam("description_seo") String descriptionSeo,
                                 RedirectAttributes redirectAttributes) {

        // Здесь предполагается, что объект MainPage уже содержит данные из формы,
        // переданные через th:object в представлении.

        // Выполните поиск существующего объекта MainPage по его id в базе данных.
        Optional<Advertising> existingMainPage = advertisingRepository.findById(1L);

        if (existingMainPage.isPresent()) {
            Advertising currentPage = existingMainPage.get();

            // Обновите поля существующего объекта MainPage данными из формы.
            currentPage.setAdvertising_name(advertising_name);
            currentPage.setDescription(description);


            if ("on".equals(status)) {
                // Обработка, когда статус включен
                currentPage.setStatus("on");
            } else {
                // Обработка, когда статус выключен или отсутствует
                currentPage.setStatus("off");
            }

            currentPage.setSeo_url(url);
            currentPage.setSeo_keywords(keywords);
            currentPage.setSeo_title(title);
            currentPage.setSeo_description(descriptionSeo);

            // Обновление главного изображения, если выбран новый файл
            String mainImagePath = saveImage(mainImageFile, currentPage.getMain_image_path());
            if (mainImagePath != null) {
                currentPage.setMain_image_path(mainImagePath);
            }

            // Обновление image_path_1, если выбран новый файл
            String imagePath1 = saveImage(imageFile1, currentPage.getImage_path_1());
            if (imagePath1 != null) {
                currentPage.setImage_path_1(imagePath1);
            }

            // Обновление image_path_1, если выбран новый файл
            String imagePath2 = saveImage(imageFile2, currentPage.getImage_path_2());
            if (imagePath2 != null) {
                currentPage.setImage_path_2(imagePath2);
            }

            // Обновление image_path_1, если выбран новый файл
            String imagePath3 = saveImage(imageFile3, currentPage.getImage_path_3());
            if (imagePath3 != null) {
                currentPage.setImage_path_3(imagePath3);
            }

            // Обновление image_path_1, если выбран новый файл
            String imagePath4 = saveImage(imageFile4, currentPage.getImage_path_4());
            if (imagePath1 != null) {
                currentPage.setImage_path_4(imagePath4);
            }

            // Обновление image_path_1, если выбран новый файл
            String imagePath5 = saveImage(imageFile5, currentPage.getImage_path_5());
            if (imagePath5 != null) {
                currentPage.setImage_path_5(imagePath5);
            }

            // Повторите для image_path_2, image_path_3, image_path_4 и image_path_5

            // Сохраните обновленный объект MainPage в базе данных.
            advertisingRepository.save(currentPage);

            // Вы можете добавить атрибуты Flash для отображения сообщений об успешном обновлении, если хотите.
            redirectAttributes.addFlashAttribute("successMessage", "Данные успешно обновлены.");
        } else {
            // Если объект MainPage с заданным id не найден, выполните соответствующие действия.
            // Например, можно создать новый объект и сохранить его, если это приемлемо.
            // Или вы можете выбрать другую логику обработки в этом случае.
        }

        return "redirect:/admin/pages";
    }

    @Value("${spring.pathImg}")
    private String pathPhotos;

    private String saveImage(MultipartFile file, String currentImagePath) {
        if (file != null && !file.isEmpty()) {
            try {
                String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
                String fileExtension = getFileExtension(originalFileName);
                String uniqueFileName = generateUniqueFileName(fileExtension);

                Path filePath = Paths.get(pathPhotos, uniqueFileName);
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
