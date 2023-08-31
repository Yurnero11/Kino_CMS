package com.example.Kino_CMS.controller.adminController;

import com.example.Kino_CMS.entity.Gallary;
import com.example.Kino_CMS.entity.Promotion;
import com.example.Kino_CMS.entity.SeoBlock;
import com.example.Kino_CMS.service.impl.GallaryServiceImpl;
import com.example.Kino_CMS.service.impl.PromotionServiceImpl;
import com.example.Kino_CMS.service.impl.SeoBlocksServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired
    private PromotionServiceImpl promotionServiceImpl;
    @Autowired
    private SeoBlocksServiceImpl seoBlocksService;
    @Autowired
    private GallaryServiceImpl gallaryService;

    @GetMapping("/admin/promotions")
    public String promotions(Model model){
        Iterable<Promotion> promotions = promotionServiceImpl.getAllPromotions(); // Здесь используйте ваш сервис или репозиторий для получения списка кинотеатров
        model.addAttribute("promotion", promotions);
        return "admin/promotion/promotions-page";
    }

    @GetMapping("/admin/promotions/promotions-add")
    public String promotionAdd(){
        return "admin/promotion/promotions-add";
    }

    @PostMapping("/admin/promotions/promotions-add")
    public String promotion_add(
            @RequestParam("promotion_name") String news_name,
            @RequestParam("description") String description,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate publication_date,
            @RequestParam("main_image_path") MultipartFile main_photo,
            @RequestParam("gallery_photo_1") MultipartFile upload1,
            @RequestParam("gallery_photo_2") MultipartFile upload2,
            @RequestParam("gallery_photo_3") MultipartFile upload3,
            @RequestParam("gallery_photo_4") MultipartFile upload4,
            @RequestParam("gallery_photo_5") MultipartFile upload5,
            @RequestParam(value = "status", required = false, defaultValue = "off") String status,
            @RequestParam("url") String url,
            @RequestParam("title") String title,
            @RequestParam("keywords") String keywords,
            @RequestParam("description_seo") String descriptionSeo,
            RedirectAttributes redirectAttributes
    ) {
        SeoBlock seoBlock = new SeoBlock();
        Gallary gallary = new Gallary();
        Promotion promotion = new Promotion();


        promotion.setPromotion_title(news_name);
        promotion.setPromotion_description(description);
        promotion.setPublication_date(publication_date);
        promotion.setMain_image_path(saveImage(main_photo));
        if ("on".equals(status)) {
            // Обработка, когда статус включен
            promotion.setStatus("on");
        } else {
            // Обработка, когда статус выключен или отсутствует
            promotion.setStatus("off");
        }
        gallary.setImagePath1(saveImage(upload1));
        gallary.setImagePath2(saveImage(upload2));
        gallary.setImagePath3(saveImage(upload3));
        gallary.setImagePath4(saveImage(upload4));
        gallary.setImagePath5(saveImage(upload5));
        seoBlock.setUrl(url);
        seoBlock.setTitle(title);
        seoBlock.setKeywords(keywords);
        seoBlock.setDescription(descriptionSeo);

        promotion.setGallery(gallary);
        promotion.setSeoBlock(seoBlock);

        promotionServiceImpl.savePromotions(promotion);
        gallaryService.saveGallary(gallary);
        seoBlocksService.saveSeoBlock(seoBlock);

        return "redirect:/admin/promotions";
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Value("${spring.pathImg}")
    private String pathPhotos;

    private String saveImage(MultipartFile file) {
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
