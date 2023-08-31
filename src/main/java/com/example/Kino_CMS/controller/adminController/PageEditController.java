package com.example.Kino_CMS.controller.adminController;

import com.example.Kino_CMS.entity.Page;
import com.example.Kino_CMS.repository.PageRepository;
import com.example.Kino_CMS.service.impl.PageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Optional;
import java.util.UUID;

@Controller
public class PageEditController {
    @Autowired
    private PageServiceImpl pageService;

    @Autowired
    private PageRepository pageRepository;


    @GetMapping("/admin/pages/{page_id}/edit")
    public String editPage(@PathVariable("page_id") long id, Model model) {
        if (!pageRepository.existsById(id)) {
            return "redirect:/admin/pages";
        }

        Optional<Page> pagesOptional = pageService.findById(id);
        if (pagesOptional.isPresent()) {
            Page page = pagesOptional.get();
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
                           @RequestParam(value = "status", required = false, defaultValue = "off") String status,
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
        Optional<Page> optionalPages = pageService.findById(id);
        if (optionalPages.isEmpty()) {
            return "redirect:/admin/pages";
        }

        Page page = optionalPages.get();
        page.setPage_name(page_name);
        page.setDescription(description);
        if ("on".equals(status)) {
            // Обработка, когда статус включен
            page.setStatus("on");
        } else {
            // Обработка, когда статус выключен или отсутствует
            page.setStatus("off");
        }
        page.setMain_image_path(saveImage(main_image_path, page.getMain_image_path()));

        page.setImage_path_1(saveImage(upload1, page.getImage_path_1()));
        page.setImage_path_2(saveImage(upload2, page.getImage_path_2()));
        page.setImage_path_3(saveImage(upload3, page.getImage_path_3()));
        page.setImage_path_4(saveImage(upload4, page.getImage_path_4()));
        page.setImage_path_5(saveImage(upload5, page.getImage_path_5()));

        page.setSeo_url(url);
        page.setSeo_title(title);
        page.setSeo_keywords(keywords);
        page.setSeo_description(descriptionSeo);

        if (main_image_path != null && !main_image_path.isEmpty()) {
            page.setMain_image_path(saveImage(main_image_path, page.getMain_image_path()));
        } else {
            page.setMain_image_path(page.getMain_image_path()); // Сохраняем старое значение
        }

        pageService.savePages(page);
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
