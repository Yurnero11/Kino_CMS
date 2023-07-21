package com.example.Kino_CMS.controller;

import com.example.Kino_CMS.entity.Cinemas;
import com.example.Kino_CMS.entity.Pages;
import com.example.Kino_CMS.repository.PageRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Controller
public class PageController {
    private final PageRepository pageRepository;

    public PageController(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    @GetMapping("/pages/page-add")
    public String pageAdd(Model model){
        return "pages/page-add";
    }

    @PostMapping("/pages/page-add")
    public String pageAdd(
            @RequestParam("name") String page_name,
            @RequestParam("status") String status,
            @RequestParam("description") String description,
            @RequestParam("mainImageFile") MultipartFile main_image_path,
            @RequestParam("gallery_photo_1") MultipartFile upload1,
            @RequestParam("gallery_photo_2") MultipartFile upload2,
            @RequestParam("gallery_photo_3") MultipartFile upload3,
            @RequestParam("gallery_photo_4") MultipartFile upload4,
            @RequestParam("gallery_photo_5") MultipartFile upload5,
            @RequestParam("url") String url,
            @RequestParam("title") String title,
            @RequestParam("keywords") String keywords,
            @RequestParam("description_seo") String descriptionSeo,
            RedirectAttributes redirectAttributes
    ){
        Pages pages = new Pages();
        pages.setPage_name(page_name);
        pages.setStatus(status);
        pages.setDescription(description);
        pages.setMain_image_path(saveImage(main_image_path));
        pages.setImage_path_1(saveImage(upload1));
        pages.setImage_path_2(saveImage(upload2));
        pages.setImage_path_3(saveImage(upload3));
        pages.setImage_path_4(saveImage(upload4));
        pages.setImage_path_5(saveImage(upload5));
        pages.setSeo_url(url);
        pages.setSeo_title(title);
        pages.setSeo_keywords(keywords);
        pages.setSeo_description(descriptionSeo);

        pageRepository.save(pages);

        return "redirect:/pages";
    }

    @PostMapping("/pages/{page_id}/delete")
    public String deletePages(@PathVariable(value = "page_id") long id, Model model){
        Pages pages = pageRepository.findById(id).orElseThrow();
        pageRepository.delete(pages);
        return "redirect:/pages";
    }

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
