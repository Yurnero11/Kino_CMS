package com.example.Kino_CMS.controller.adminController;

import com.example.Kino_CMS.entity.Cinema;
import com.example.Kino_CMS.repository.CinemaRepository;
import com.example.Kino_CMS.service.impl.CinemaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class CinemaEditController {
    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private CinemaServiceImpl service;

    @GetMapping("/admin/cinemas/{cinema_id}/edit")
    public String editCinema(@PathVariable(value = "cinema_id") long id, Model model) {
        if (!cinemaRepository.existsById(id)) {
            return "redirect:/admin/cinemas";
        }

        Optional<Cinema> optionalCinema = cinemaRepository.findById(id);
        if (optionalCinema.isEmpty()) {
            return "redirect:/admin/cinemas";
        }

        Cinema cinema = optionalCinema.get();

        // Добавление объекта cinema в модель
        model.addAttribute("cinema", cinema);

        return "admin/cinemas/cinema-edit";
    }

    @PostMapping("/admin/cinemas/{cinema_id}/edit")
    public String editCinemaSubmit(@PathVariable(value = "cinema_id") long id,
                                   @RequestParam("name") String cinemaName,
                                   @RequestParam("description") String description,
                                   @RequestParam("conditions") String conditions,
                                   @RequestParam(value = "logotype", required = false) MultipartFile logotype,
                                   @RequestParam("head_banner") MultipartFile headBanner,
                                   @RequestParam("gallery_photo_1") MultipartFile upload1,
                                   @RequestParam("gallery_photo_2") MultipartFile upload2,
                                   @RequestParam("gallery_photo_3") MultipartFile upload3,
                                   @RequestParam("gallery_photo_4") MultipartFile upload4,
                                   @RequestParam("gallery_photo_5") MultipartFile upload5,
                                   @RequestParam("seoBlock.url") String url,
                                   @RequestParam("seoBlock.title") String title,
                                   @RequestParam("seoBlock.keywords") String keywords,
                                   @RequestParam("seoBlock.description") String descriptionSeo,
                                   RedirectAttributes redirectAttributes) {

        Optional<Cinema> optionalCinema = service.getCinemaById(id);
        if (optionalCinema.isEmpty()) {
            return "redirect:/admin/cinemas";
        }

        Cinema cinema = optionalCinema.get();
        cinema.setName(cinemaName);
        cinema.setDescription(description);
        cinema.setConditions(conditions);
        cinema.setLogo_image_path(saveImage(logotype, cinema.getLogo_image_path()));
        cinema.setTop_banner_image_path(saveImage(headBanner, cinema.getTop_banner_image_path()));
        cinema.getGallery().setImagePath1(saveImage(upload1, cinema.getGallery().getImagePath1()));
        cinema.getGallery().setImagePath2(saveImage(upload2, cinema.getGallery().getImagePath2()));
        cinema.getGallery().setImagePath3(saveImage(upload3, cinema.getGallery().getImagePath3()));
        cinema.getGallery().setImagePath4(saveImage(upload4, cinema.getGallery().getImagePath4()));
        cinema.getGallery().setImagePath5(saveImage(upload5, cinema.getGallery().getImagePath5()));
        cinema.getSeoBlock().setUrl(url);
        cinema.getSeoBlock().setTitle(title);
        cinema.getSeoBlock().setKeywords(keywords);
        cinema.getSeoBlock().setDescription(descriptionSeo);

        if (logotype != null && !logotype.isEmpty()) {
            cinema.setLogo_image_path(saveImage(logotype, cinema.getLogo_image_path()));
        } else {
            cinema.setLogo_image_path(cinema.getLogo_image_path()); // Сохраняем старое значение
        }

        service.saveCinemas(cinema);

        return "redirect:/admin/cinemas";
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
