package com.example.Kino_CMS.controller;

import com.example.Kino_CMS.entity.Gallary;
import com.example.Kino_CMS.entity.Promotions;
import com.example.Kino_CMS.entity.SeoBlocks;
import com.example.Kino_CMS.repository.GalleryRepository;
import com.example.Kino_CMS.repository.PromotionRepository;
import com.example.Kino_CMS.repository.SeoBlocksRepository;
import com.example.Kino_CMS.service.PromotionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.UUID;

@Controller
public class PromotionController {
    private final GalleryRepository galleryRepository;
    private final SeoBlocksRepository seoBlocksRepository;
    private final PromotionRepository promotionRepository;

    private final PromotionService promotionService;

    public PromotionController(GalleryRepository galleryRepository, SeoBlocksRepository seoBlocksRepository, PromotionRepository promotionRepository, PromotionService promotionService) {
        this.galleryRepository = galleryRepository;
        this.seoBlocksRepository = seoBlocksRepository;

        this.promotionRepository = promotionRepository;
        this.promotionService = promotionService;
    }

    @GetMapping("/promotions")
    public String promotions(Model model){
        Iterable<Promotions> promotions = promotionService.getAllPromotions(); // Здесь используйте ваш сервис или репозиторий для получения списка кинотеатров
        model.addAttribute("promotion", promotions);
        return "/promotion/promotions-page";
    }

    @GetMapping("/promotions/promotions-add")
    public String promotionAdd(){
        return "/promotion/promotions-add";
    }

    @PostMapping("/promotions/promotions-add")
    public String promotion_add(
            @RequestParam("promotion_name") String news_name,
            @RequestParam("description") String description,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate publication_date,
            @RequestParam("main_photo") MultipartFile main_photo,
            @RequestParam("gallery_photo_1") MultipartFile upload1,
            @RequestParam("gallery_photo_2") MultipartFile upload2,
            @RequestParam("gallery_photo_3") MultipartFile upload3,
            @RequestParam("gallery_photo_4") MultipartFile upload4,
            @RequestParam("gallery_photo_5") MultipartFile upload5,
            @RequestParam("status") String status,
            @RequestParam("url") String url,
            @RequestParam("title") String title,
            @RequestParam("keywords") String keywords,
            @RequestParam("description_seo") String descriptionSeo,
            RedirectAttributes redirectAttributes
    ) {
        SeoBlocks seoBlocks = new SeoBlocks();
        Gallary gallary = new Gallary();
        Promotions promotions = new Promotions();


        promotions.setPromotion_title(news_name);
        promotions.setPromotion_description(description);
        promotions.setPublication_date(publication_date);
        promotions.setMain_image_path(saveImage(main_photo));
        promotions.setStatus(status);
        gallary.setImagePath1(saveImage(upload1));
        gallary.setImagePath2(saveImage(upload2));
        gallary.setImagePath3(saveImage(upload3));
        gallary.setImagePath4(saveImage(upload4));
        gallary.setImagePath5(saveImage(upload5));
        seoBlocks.setUrl(url);
        seoBlocks.setTitle(title);
        seoBlocks.setKeywords(keywords);
        seoBlocks.setDescription(descriptionSeo);

        promotions.setGallery(gallary);
        promotions.setSeoBlocks(seoBlocks);

        promotionRepository.save(promotions);
        galleryRepository.save(gallary);
        seoBlocksRepository.save(seoBlocks);

        return "redirect:/promotions";
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private final String uploadDir = "upload";

    private String saveImage(MultipartFile file) {
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
            return null;
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
