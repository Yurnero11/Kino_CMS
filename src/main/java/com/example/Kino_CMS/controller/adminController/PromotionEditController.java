package com.example.Kino_CMS.controller.adminController;

import com.example.Kino_CMS.entity.Promotions;
import com.example.Kino_CMS.repository.GalleryRepository;
import com.example.Kino_CMS.repository.PromotionRepository;
import com.example.Kino_CMS.repository.SeoBlocksRepository;
import com.example.Kino_CMS.service.impl.GallaryServiceImpl;
import com.example.Kino_CMS.service.impl.PromotionServiceImpl;
import com.example.Kino_CMS.service.impl.SeoBlocksServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Controller
public class PromotionEditController {
    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private PromotionServiceImpl promotionServiceImpl;
    @Autowired
    private SeoBlocksServiceImpl seoBlocksService;
    @Autowired
    private GallaryServiceImpl gallaryService;



    @GetMapping("/admin/promotions/{promotion_id}/edit")
    public String editCinema(@PathVariable(value = "promotion_id") long id, Model model) {
        if (!promotionRepository.existsById(id)) {
            return "redirect:/admin/promotions";
        }

        Optional<Promotions> promotionsOptional = promotionRepository.findById(id);
        if (promotionsOptional.isEmpty()) {
            return "redirect:/admin/promotions";
        }

        Promotions promotions = promotionsOptional.get();

        // Добавление объекта cinema в модель
        model.addAttribute("promotions", promotions);

        return "admin/promotion/promotions-edit";
    }

    @PostMapping("/admin/promotions/{promotion_id}/edit")
    public String promotionEdit(
            @PathVariable(value = "promotion_id") long id,
            @RequestParam("promotion_name") String promotion_name,
            @RequestParam("status") String status,
            @RequestParam("promotion_description") String promotion_description,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate publication_date,
            @RequestParam("main_image_path") MultipartFile main_image_path,
            @RequestParam("gallery_photo_1") MultipartFile upload1,
            @RequestParam("gallery_photo_2") MultipartFile upload2,
            @RequestParam("gallery_photo_3") MultipartFile upload3,
            @RequestParam("gallery_photo_4") MultipartFile upload4,
            @RequestParam("gallery_photo_5") MultipartFile upload5,
            @RequestParam("url") String url,
            @RequestParam("title") String title,
            @RequestParam("keywords") String keywords,
            @RequestParam("description_seo") String descriptionSeo)
    {
        Optional<Promotions> promotionsOptional = promotionRepository.findById(id);
        if (promotionsOptional.isEmpty()) {
            return "redirect:/admin/promotions";
        }

        Promotions promotions = promotionsOptional.get();
        promotions.setPromotion_title(promotion_name);
        promotions.setPromotion_description(promotion_description);
        promotions.setPublication_date(publication_date);
        promotions.setStatus(status);
        promotions.setMain_image_path(saveImage(main_image_path, promotions.getMain_image_path()));
        promotions.getGallery().setImagePath1(saveImage(upload1, promotions.getGallery().getImagePath1()));
        promotions.getGallery().setImagePath2(saveImage(upload2, promotions.getGallery().getImagePath2()));
        promotions.getGallery().setImagePath3(saveImage(upload3, promotions.getGallery().getImagePath3()));
        promotions.getGallery().setImagePath4(saveImage(upload4, promotions.getGallery().getImagePath4()));
        promotions.getGallery().setImagePath5(saveImage(upload5, promotions.getGallery().getImagePath5()));
        promotions.getSeoBlocks().setUrl(url);
        promotions.getSeoBlocks().setTitle(title);
        promotions.getSeoBlocks().setKeywords(keywords);
        promotions.getSeoBlocks().setDescription(descriptionSeo);

        if (main_image_path != null && !main_image_path.isEmpty()) {
            promotions.setMain_image_path(saveImage(main_image_path, promotions.getMain_image_path()));
        } else {
            promotions.setMain_image_path(promotions.getMain_image_path()); // Сохраняем старое значение
        }

        promotionServiceImpl.savePromotions(promotions);

        return "redirect:/admin/promotions";
    }

    @PostMapping("/admin/promotions/{promotions_id}/remove")
    public String removePromotion(@PathVariable("promotions_id") long promotion_id) {
        Optional<Promotions> optionalPromotions = promotionRepository.findById(promotion_id);
        if (optionalPromotions.isEmpty()) {
            // Обработка ошибки - акция не найдена
            return "redirect:/admin/promotions";
        }

        Promotions promotion = optionalPromotions.get();

        // Удаление изображения из папки, если путь к изображению указан
        if (promotion.getMain_image_path() != null) {
            deleteImage(promotion.getMain_image_path());
        }

        promotionServiceImpl.delete(promotion);

        return "redirect:/admin/promotions";
    }

    private void deleteImage(String imageFilename) {
        String uploadPath = "upload";
        Path imagePath = Paths.get(uploadPath, imageFilename);

        try {
            Files.delete(imagePath);
        } catch (IOException e) {
            // Handle the exception, e.g., log an error
            e.printStackTrace();
        }
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
