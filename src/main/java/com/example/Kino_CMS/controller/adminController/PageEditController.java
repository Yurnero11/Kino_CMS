package com.example.Kino_CMS.controller.adminController;

import com.example.Kino_CMS.entity.Cinemas;
import com.example.Kino_CMS.entity.Pages;
import com.example.Kino_CMS.entity.User;
import com.example.Kino_CMS.repository.PageRepository;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Controller
public class PageEditController {
    private final PageRepository pageRepository;

    public PageEditController(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    @GetMapping("/admin/pages/{page_id}/edit")
    public String editPage(@PathVariable("page_id") long id, Model model) {
        if (!pageRepository.existsById(id)) {
            return "redirect:/admin/pages";
        }

        Optional<Pages> pagesOptional = pageRepository.findById(id);
        if (pagesOptional.isPresent()) {
            Pages page = pagesOptional.get();
            model.addAttribute("pages", page);
            return "admin/pages/page-edit";
        } else {
            return "redirect:/admin/pages";
        }
    }

    @PostMapping("/admin/pages/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editUser(@PathVariable(value = "id") long id,
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
        Optional<Pages> optionalPages = pageRepository.findById(id);
        if (optionalPages.isEmpty()) {
            return "redirect:/admin/pages";
        }

        Pages pages = optionalPages.get();
        pages.setPage_name(page_name);
        pages.setDescription(description);
        pages.setStatus(status);
        pages.setMain_image_path(saveImage(main_image_path, pages.getMain_image_path()));

        pages.setImage_path_1(saveImage(upload1, pages.getImage_path_1()));
        pages.setImage_path_2(saveImage(upload2, pages.getImage_path_2()));
        pages.setImage_path_3(saveImage(upload3, pages.getImage_path_3()));
        pages.setImage_path_4(saveImage(upload4, pages.getImage_path_4()));
        pages.setImage_path_5(saveImage(upload5, pages.getImage_path_5()));

        pages.setSeo_url(url);
        pages.setSeo_title(title);
        pages.setSeo_keywords(keywords);
        pages.setSeo_description(descriptionSeo);

        if (main_image_path != null && !main_image_path.isEmpty()) {
            pages.setMain_image_path(saveImage(main_image_path, pages.getMain_image_path()));
        } else {
            pages.setMain_image_path(pages.getMain_image_path()); // Сохраняем старое значение
        }

        pageRepository.save(pages);
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
