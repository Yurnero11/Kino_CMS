package com.example.Kino_CMS.controller.adminController;

import com.example.Kino_CMS.entity.BackgroundBanner;
import com.example.Kino_CMS.entity.LastBanner;
import com.example.Kino_CMS.entity.MainBanners;
import com.example.Kino_CMS.repository.BackgroundBannerRepository;
import com.example.Kino_CMS.repository.LastBannerRepository;
import com.example.Kino_CMS.repository.MainBannersRepository;
import com.example.Kino_CMS.service.BackgroundBannerService;
import com.example.Kino_CMS.service.LastBannerService;
import com.example.Kino_CMS.service.MainBannerService;
import com.example.Kino_CMS.service.impl.BackgroundBannerServiceImpl;
import com.example.Kino_CMS.service.impl.LastBannerServiceImpl;
import com.example.Kino_CMS.service.impl.MainBannerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.UUID;

@Controller
public class BannerController {
    @Autowired
    private MainBannerServiceImpl mainBannerService;
    @Autowired
    private BackgroundBannerServiceImpl backgroundBannerService;
    @Autowired
    private LastBannerServiceImpl lastBannerService;


    @GetMapping("/admin/banners")
    public String banners(Model model) {
        // Логика для отображения списка баннеров, если необходимо
        return "admin/customers/banners";
    }

    @PostMapping("/admin/banners/main-banner")
    public String addMainBanner(
            @RequestParam("upload1") MultipartFile upload1,
            @RequestParam("upload2") MultipartFile upload2,
            @RequestParam("upload3") MultipartFile upload3,
            @RequestParam("upload4") MultipartFile upload4,
            @RequestParam("upload5") MultipartFile upload5,
            @RequestParam("url1") String url1,
            @RequestParam("url2") String url2,
            @RequestParam("url3") String url3,
            @RequestParam("url4") String url4,
            @RequestParam("url5") String url5,
            @RequestParam("caption1") String caption1,
            @RequestParam("caption2") String caption2,
            @RequestParam("caption3") String caption3,
            @RequestParam("caption4") String caption4,
            @RequestParam("caption5") String caption5,
            @RequestParam("rotation_speed") int rotation_speed,
            RedirectAttributes redirectAttributes
    ) {
        // Создание объекта MainBanners
        MainBanners mainBanners = new MainBanners();


        // Сохранение URL и текста в соответствующие поля модели
        mainBanners.setUrl1(url1);
        mainBanners.setUrl2(url2);
        mainBanners.setUrl3(url3);
        mainBanners.setUrl4(url4);
        mainBanners.setUrl5(url5);

        mainBanners.setText1(caption1);
        mainBanners.setText2(caption2);
        mainBanners.setText3(caption3);
        mainBanners.setText4(caption4);
        mainBanners.setText5(caption5);

        mainBanners.setImagePath1(saveImage(upload1));
        mainBanners.setImagePath2(saveImage(upload2));
        mainBanners.setImagePath3(saveImage(upload3));
        mainBanners.setImagePath4(saveImage(upload4));
        mainBanners.setImagePath5(saveImage(upload5));

        mainBanners.setSpeed(rotation_speed);

        // Сохранение модели в базу данных
        mainBannerService.saveMainBanners(mainBanners);

        // Перенаправление с атрибутом успеха
        redirectAttributes.addFlashAttribute("successMessage", "Баннеры успешно добавлены.");

        return "redirect:/admin/banners";
    }

    @PostMapping("/admin/banners/back-banner")
    public String addBackBanner(
        @RequestParam("upload6") MultipartFile upload6,
        @RequestParam("photo_type") String photo_type,
        RedirectAttributes redirectAttributes
        ){

        BackgroundBanner backgroundBanner = new BackgroundBanner();
        backgroundBanner.setBackground_type(photo_type);
        backgroundBanner.setImage_path(saveImage(upload6));

        backgroundBannerService.saveBackgroundBanner(backgroundBanner);
        redirectAttributes.addFlashAttribute("successMessage", "Баннеры успешно добавлены.");

        return "redirect:/admin/banners";
    }

    @PostMapping("/admin/banners/stocks-banner")
    public String addStocksBanner(
            @RequestParam("upload1") MultipartFile upload1,
            @RequestParam("upload2") MultipartFile upload2,
            @RequestParam("upload3") MultipartFile upload3,
            @RequestParam("upload4") MultipartFile upload4,
            @RequestParam("upload5") MultipartFile upload5,
            @RequestParam("url1") String url1,
            @RequestParam("url2") String url2,
            @RequestParam("url3") String url3,
            @RequestParam("url4") String url4,
            @RequestParam("url5") String url5,
            @RequestParam("rotation_speed") int rotation_speed,
            RedirectAttributes redirectAttributes){
        LastBanner lastBanner = new LastBanner();

        lastBanner.setUrl1(url1);
        lastBanner.setUrl2(url2);
        lastBanner.setUrl3(url3);
        lastBanner.setUrl4(url4);
        lastBanner.setUrl5(url5);

        lastBanner.setImagePath1(saveImage(upload1));
        lastBanner.setImagePath2(saveImage(upload2));
        lastBanner.setImagePath3(saveImage(upload3));
        lastBanner.setImagePath4(saveImage(upload4));
        lastBanner.setImagePath5(saveImage(upload5));

        lastBanner.setSpeed(rotation_speed);

        lastBannerService.saveLastBanner(lastBanner);
        redirectAttributes.addFlashAttribute("successMessage", "Баннеры успешно добавлены.");

        return "redirect:/admin/banners";
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
