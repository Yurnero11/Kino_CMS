package com.example.Kino_CMS.controller.adminController;

import com.example.Kino_CMS.entity.*;
import com.example.Kino_CMS.repository.CinemaRepository;
import com.example.Kino_CMS.repository.GalleryRepository;
import com.example.Kino_CMS.repository.HallRepository;
import com.example.Kino_CMS.repository.SeoBlocksRepository;
import com.example.Kino_CMS.service.impl.CinemaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.*;

@Controller
public class CinemaController {
    private final SeoBlocksRepository seoBlocksRepository;
    private final GalleryRepository galleryRepository;
    private final CinemaRepository cinemaRepository;
    private final HallRepository hallRepository;

    @Autowired
    private CinemaServiceImpl service;

    public CinemaController(SeoBlocksRepository seoBlocksRepository, GalleryRepository galleryRepository, CinemaRepository cinemaRepository, HallRepository hallRepository) {
        this.seoBlocksRepository = seoBlocksRepository;
        this.galleryRepository = galleryRepository;
        this.cinemaRepository = cinemaRepository;
        this.hallRepository = hallRepository;
    }


    @GetMapping("/admin/cinemas")
    public String cinemas(Model model){
        Iterable<Cinemas> cinemas = service.getAllCinemas(); // Здесь используйте ваш сервис или репозиторий для получения списка кинотеатров
        model.addAttribute("cinemas", cinemas);
        return "admin/cinemas/page-cinema";
    }

    @GetMapping("/admin/cinemas/cinema-add")
    public String cinemas_add(Model model) {
        // Создайте пустой объект Cinema и передайте его в модель
        model.addAttribute("cinema", new Cinemas());
        return "admin/cinemas/cinema-add";
    }

    @PostMapping("/admin/cinemas/cinema-add")
    public String cinema_add(
            @RequestParam("cinema_name") String cinemaName,
            @RequestParam("description") String description,
            @RequestParam("conditions") MultipartFile conditions,
            @RequestParam("logotype") MultipartFile logotype,
            @RequestParam("head_banner") MultipartFile headBanner,
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
    ) {
        SeoBlocks seoBlocks = new SeoBlocks();
        Gallary gallary = new Gallary();
        Cinemas cinema = new Cinemas();


        cinema.setName(cinemaName);
        cinema.setDescription(description);
        cinema.setConditions(saveImage(conditions));
        cinema.setLogo_image_path(saveImage(logotype));
        cinema.setTop_banner_image_path(saveImage(headBanner));
        gallary.setImagePath1(saveImage(upload1));
        gallary.setImagePath2(saveImage(upload2));
        gallary.setImagePath3(saveImage(upload3));
        gallary.setImagePath4(saveImage(upload4));
        gallary.setImagePath5(saveImage(upload5));
        seoBlocks.setUrl(url);
        seoBlocks.setTitle(title);
        seoBlocks.setKeywords(keywords);
        seoBlocks.setDescription(descriptionSeo);

        cinema.setGallery(gallary);
        cinema.setSeoBlocks(seoBlocks);

        cinemaRepository.save(cinema);
        galleryRepository.save(gallary);
        seoBlocksRepository.save(seoBlocks);

        return "redirect:/admin/cinemas";
    }

    @GetMapping("/admin/cinemas/{cinema_id}/edit/hall-add")
    public String showAddHallForm(@PathVariable("cinema_id") long cinemaId, Model model) {
        model.addAttribute("cinemaId", cinemaId);
        return "admin/cinemas/hall-add";
    }

    @PostMapping("/admin/cinemas/{cinema_id}/edit/hall-add")
    public String addHall(@PathVariable("cinema_id") long cinemaId,
                          @RequestParam("hall_number") int hallNumber,
                          @RequestParam("descriptions") String descriptions,
                          @RequestParam("schema") MultipartFile schema,
                          @RequestParam("head_banner") MultipartFile headBanner,
                          @RequestParam("gallery_photo_1") MultipartFile galleryPhoto1,
                          @RequestParam("gallery_photo_2") MultipartFile galleryPhoto2,
                          @RequestParam("gallery_photo_3") MultipartFile galleryPhoto3,
                          @RequestParam("gallery_photo_4") MultipartFile galleryPhoto4,
                          @RequestParam("gallery_photo_5") MultipartFile galleryPhoto5,
                          @RequestParam("url") String url,
                          @RequestParam("title") String title,
                          @RequestParam("keywords") String keywords,
                          @RequestParam("description_seo") String descriptionSeo) {

        Optional<Cinemas> optionalCinema = cinemaRepository.findById(cinemaId);
        if (optionalCinema.isEmpty()) {
            // Обработка ошибки - кинотеатр не найден
            return "redirect:/cinemas";
        }

        Cinemas cinema = optionalCinema.get();
        Gallary gallary = new Gallary();
        SeoBlocks seoBlocks = new SeoBlocks();

        Halls hall = new Halls();
        hall.setNumber(hallNumber);
        hall.setDescription(descriptions);
        hall.setSchema_image_path(saveImage(schema));
        hall.setTop_banner_image_path(saveImage(headBanner));
        gallary.setImagePath1(saveImage(galleryPhoto1));
        gallary.setImagePath2(saveImage(galleryPhoto2));
        gallary.setImagePath3(saveImage(galleryPhoto3));
        gallary.setImagePath4(saveImage(galleryPhoto4));
        gallary.setImagePath5(saveImage(galleryPhoto5));

        seoBlocks.setUrl(url);
        seoBlocks.setTitle(title);
        seoBlocks.setKeywords(keywords);
        seoBlocks.setDescription(descriptionSeo);

        hall.setGallery(gallary);
        hall.setSeoBlocks(seoBlocks);

        hall.setCinema(cinema); // Связывание зала с кинотеатром

        hallRepository.save(hall);

        return "redirect:/admin/cinemas";
    }


    @GetMapping("/admin/cinemas/{cinema_id}/edit/{hall_id}/edit")
    public String showEditHallForm(@PathVariable("cinema_id") long cinemaId,
                                   @PathVariable("hall_id") long hallId,
                                   Model model) {
        Optional<Cinemas> optionalCinema = cinemaRepository.findById(cinemaId);
        if (optionalCinema.isEmpty()) {
            // Обработка ошибки - кинотеатр не найден
            return "redirect:/admin/cinemas";
        }

        Optional<Halls> optionalHall = hallRepository.findById(hallId);
        if (optionalHall.isEmpty()) {
            // Обработка ошибки - зал не найден
            return "redirect:/admin/cinemas";
        }

        Cinemas cinema = optionalCinema.get();
        Halls hall = optionalHall.get();

        model.addAttribute("cinemaId", cinemaId);
        model.addAttribute("hall", hall);
        return "/admin/cinemas/hall-edit";
    }

    @PostMapping("/admin/cinemas/{cinema_id}/edit/{hall_id}/edit")
    public String editHall(@PathVariable("cinema_id") long cinemaId,
                           @PathVariable("hall_id") long hallId,
                           @RequestParam("hall_number") int hallNumber,
                           @RequestParam("descriptions") String descriptions,
                           @RequestParam("schema") MultipartFile schema,
                           @RequestParam("head_banner") MultipartFile headBanner,
                           @RequestParam("gallery_photo_1") MultipartFile galleryPhoto1,
                           @RequestParam("gallery_photo_2") MultipartFile galleryPhoto2,
                           @RequestParam("gallery_photo_3") MultipartFile galleryPhoto3,
                           @RequestParam("gallery_photo_4") MultipartFile galleryPhoto4,
                           @RequestParam("gallery_photo_5") MultipartFile galleryPhoto5,
                           @RequestParam("url") String url,
                           @RequestParam("title") String title,
                           @RequestParam("keywords") String keywords,
                           @RequestParam("description_seo") String descriptionSeo) {

        Optional<Cinemas> optionalCinema = cinemaRepository.findById(cinemaId);
        if (optionalCinema.isEmpty()) {
            // Обработка ошибки - кинотеатр не найден
            return "redirect:/admin/cinemas";
        }

        Cinemas cinema = optionalCinema.get();
        Optional<Halls> optionalHall = hallRepository.findById(hallId);
        if (optionalHall.isEmpty()) {
            // Обработка ошибки - зал не найден
            return "redirect:/admin/cinemas";
        }

        Halls hall = optionalHall.get();
        hall.setNumber(hallNumber);
        hall.setDescription(descriptions);

        // Проверяем, загружены ли новые изображения схемы и баннера
        if (!schema.isEmpty()) {
            hall.setSchema_image_path(saveImage(schema));
        }
        if (!headBanner.isEmpty()) {
            hall.setTop_banner_image_path(saveImage(headBanner));
        }

        // Проверяем, загружены ли новые галерейные фотографии
        Gallary gallery = hall.getGallery();
        if (!galleryPhoto1.isEmpty()) {
            gallery.setImagePath1(saveImage(galleryPhoto1));
        }
        if (!galleryPhoto2.isEmpty()) {
            gallery.setImagePath2(saveImage(galleryPhoto2));
        }
        if (!galleryPhoto3.isEmpty()) {
            gallery.setImagePath3(saveImage(galleryPhoto3));
        }
        if (!galleryPhoto4.isEmpty()) {
            gallery.setImagePath4(saveImage(galleryPhoto4));
        }
        if (!galleryPhoto5.isEmpty()) {
            gallery.setImagePath5(saveImage(galleryPhoto5));
        }

        // Обновляем SEO-блок
        SeoBlocks seoBlocks = hall.getSeoBlocks();
        seoBlocks.setUrl(url);
        seoBlocks.setTitle(title);
        seoBlocks.setKeywords(keywords);
        seoBlocks.setDescription(descriptionSeo);

        hall.setCinema(cinema); // Связывание зала с кинотеатром

        hallRepository.save(hall);

        return "redirect:/admin/cinemas";
    }

    @PostMapping("/admin/cinemas/{cinema_id}/edit/{hall_id}/remove")
    public String removeHall(@PathVariable("cinema_id") long cinemaId,
                             @PathVariable("hall_id") long hallId) {
        Optional<Cinemas> optionalCinema = cinemaRepository.findById(cinemaId);
        if (optionalCinema.isEmpty()) {
            // Обработка ошибки - кинотеатр не найден
            return "redirect:/cinemas";
        }

        Optional<Halls> optionalHall = hallRepository.findById(hallId);
        if (optionalHall.isEmpty()) {
            // Обработка ошибки - зал не найден
            return "redirect:/cinemas";
        }

        Halls hall = optionalHall.get();

        // Получите имена файлов изображений зала из базы данных
        String schemaImageFilename = hall.getSchema_image_path();
        String topBannerImageFilename = hall.getTop_banner_image_path();

        // Если есть имена файлов изображений, удаляем файлы
        if (schemaImageFilename != null && !schemaImageFilename.isEmpty()) {
            deleteImage(schemaImageFilename);
        }

        if (topBannerImageFilename != null && !topBannerImageFilename.isEmpty()) {
            deleteImage(topBannerImageFilename);
        }

        hallRepository.delete(hall);

        return "redirect:/admin/cinemas";
    }



    @GetMapping("/admin/cinemas/{cinema_id}/edit/{hall_id}/remove")
    public String showRemoveHallForm(@PathVariable("cinema_id") long cinemaId,
                                     @PathVariable("hall_id") long hallId,
                                     Model model) {
        // Проверяем, существует ли кинотеатр с заданным идентификатором
        Optional<Cinemas> optionalCinema = cinemaRepository.findById(cinemaId);
        if (optionalCinema.isEmpty()) {
            // Обработка ошибки - кинотеатр не найден
            return "redirect:/admin/cinemas";
        }

        // Получаем информацию о кинотеатре и его зале
        Cinemas cinema = optionalCinema.get();
        Optional<Halls> optionalHall = hallRepository.findById(hallId);
        if (optionalHall.isEmpty()) {
            // Обработка ошибки - зал не найден
            return "redirect:/admin/cinemas";
        }
        Halls hall = optionalHall.get();

        // Передаем информацию о кинотеатре и его зале в модель
        model.addAttribute("cinema", cinema);
        model.addAttribute("hall", hall);

        return "admin/cinemas/remove-hall-form"; // Замените "remove-hall-form" на имя представления, отображающего форму удаления зала
    }

    @PostMapping("/admin/cinemas/{cinema_id}/remove")
    @PreAuthorize("hasRole('ADMIN')")
    public String removeCinema(@PathVariable(value = "cinema_id") long id, Model model) {
        Cinemas cinemas = cinemaRepository.findById(id).orElseThrow();

        // Получите имена файлов изображений кинотеатра из базы данных
        String logoImageFilename = cinemas.getLogo_image_path();
        String topBannerImageFilename = cinemas.getTop_banner_image_path();

        // Если есть имена файлов изображений, удаляем файлы
        if (logoImageFilename != null && !logoImageFilename.isEmpty()) {
            deleteImage(logoImageFilename);
        }

        if (topBannerImageFilename != null && !topBannerImageFilename.isEmpty()) {
            deleteImage(topBannerImageFilename);
        }

        // Удаляем кинотеатр из базы данных
        cinemaRepository.delete(cinemas);

        return "redirect:/admin/cinemas";
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
}