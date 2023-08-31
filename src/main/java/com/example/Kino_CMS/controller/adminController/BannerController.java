package com.example.Kino_CMS.controller.adminController;

import com.example.Kino_CMS.entity.BackgroundBanner;
import com.example.Kino_CMS.entity.LastBanner;
import com.example.Kino_CMS.entity.MainBanner;
import com.example.Kino_CMS.service.impl.BackgroundBannerServiceImpl;
import com.example.Kino_CMS.service.impl.LastBannerServiceImpl;
import com.example.Kino_CMS.service.impl.MainBannerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
        Iterable<MainBanner> mainBannersList = mainBannerService.getAllMainBanners(); // Отримати список банерів
        model.addAttribute("mainBannersList", mainBannersList);

        BackgroundBanner backgroundBanner = backgroundBannerService.getBackgroundBanner();
        model.addAttribute("backgroundBanner", backgroundBanner);

        LastBanner lastBanner = lastBannerService.getLastBanner();
        model.addAttribute("lastBanner", lastBanner);
        //==================================================================
        // Получение значений для чекбоксов из вашей базы данных или хранилища данных
        boolean backgroundChecked = backgroundBannerService.isBackgroundCheckedForBanner(backgroundBanner);
        boolean simpleChecked = backgroundBannerService.isSimpleCheckedForBanner(backgroundBanner);

        model.addAttribute("backgroundBanner", backgroundBanner);
        model.addAttribute("backgroundChecked", backgroundChecked);
        model.addAttribute("simpleChecked", simpleChecked);

        return "admin/customers/banners";
    }

    @PostMapping("/admin/banners/main-banner")
    public String addMainBanner(
            @RequestParam("upload1") MultipartFile upload1,
            @RequestParam("upload2") MultipartFile upload2,
            @RequestParam("upload3") MultipartFile upload3,
            @RequestParam("upload4") MultipartFile upload4,
            @RequestParam("upload5") MultipartFile upload5,
            @RequestParam(value = "status", required = false, defaultValue = "off") String status,
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
        MainBanner mainBanner = mainBannerService.getMainBanners();

        // Сохранение старых значений, если ничего не добавлено
        mainBanner.setUrl1(url1.isEmpty() ? mainBanner.getUrl1() : url1);
        mainBanner.setUrl2(url2.isEmpty() ? mainBanner.getUrl2() : url2);
        mainBanner.setUrl3(url3.isEmpty() ? mainBanner.getUrl3() : url3);
        mainBanner.setUrl4(url4.isEmpty() ? mainBanner.getUrl4() : url4);
        mainBanner.setUrl5(url5.isEmpty() ? mainBanner.getUrl5() : url5);

        if ("on".equals(status)) {
            // Обработка, когда статус включен
            mainBanner.setStatus("on");
        } else {
            // Обработка, когда статус выключен или отсутствует
            mainBanner.setStatus("off");
        }

        mainBanner.setText1(caption1.isEmpty() ? mainBanner.getText1() : caption1);
        mainBanner.setText2(caption2.isEmpty() ? mainBanner.getText2() : caption2);
        mainBanner.setText3(caption3.isEmpty() ? mainBanner.getText3() : caption3);
        mainBanner.setText4(caption4.isEmpty() ? mainBanner.getText4() : caption4);
        mainBanner.setText5(caption5.isEmpty() ? mainBanner.getText5() : caption5);

        if (!upload1.isEmpty()) {
            mainBanner.setImagePath1(saveImage(upload1));
        }

        if (!upload2.isEmpty()) {
            mainBanner.setImagePath2(saveImage(upload2));
        }

        if (!upload3.isEmpty()) {
            mainBanner.setImagePath3(saveImage(upload3));
        }

        if (!upload4.isEmpty()) {
            mainBanner.setImagePath4(saveImage(upload4));
        }

        if (!upload5.isEmpty()) {
            mainBanner.setImagePath5(saveImage(upload5));
        }

        mainBanner.setSpeed(rotation_speed);

        // Сохранение обновленного баннера в базу данных
        mainBannerService.saveMainBanners(mainBanner);

        // Перенаправление с атрибутом успеха
        redirectAttributes.addFlashAttribute("successMessage", "Баннеры успешно добавлены.");

        return "redirect:/admin/banners";
    }

    @PostMapping("/admin/banners/back-banner")
    public String addBackBanner(
            @RequestParam("upload6") MultipartFile upload6,
            @RequestParam(value = "photo_type", required = false) String photo_type,
            RedirectAttributes redirectAttributes
    ) {
        if (photo_type == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Выберите хотя бы одну опцию");
            return "redirect:/admin/banners"; // Перенаправление назад с сообщением об ошибке
        }

        BackgroundBanner backgroundBanner = backgroundBannerService.getBackgroundBanner();
        backgroundBanner.setBackground_type(photo_type);

        if (!upload6.isEmpty()) {
            backgroundBanner.setImage_path(saveImage(upload6));
        }

        backgroundBannerService.saveBackgroundBanner(backgroundBanner);
        //redirectAttributes.addFlashAttribute("successMessage", "Баннеры успешно добавлены.");

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
            @RequestParam(value = "status", required = false, defaultValue = "off") String status,
            @RequestParam("rotation_speed") int rotation_speed,
            RedirectAttributes redirectAttributes){

        LastBanner lastBanner = lastBannerService.getLastBanner();

        // Сохранение старых значений, если ничего не добавлено
        lastBanner.setUrl1(url1.isEmpty() ? lastBanner.getUrl1() : url1);
        lastBanner.setUrl2(url2.isEmpty() ? lastBanner.getUrl2() : url2);
        lastBanner.setUrl3(url3.isEmpty() ? lastBanner.getUrl3() : url3);
        lastBanner.setUrl4(url4.isEmpty() ? lastBanner.getUrl4() : url4);
        lastBanner.setUrl5(url5.isEmpty() ? lastBanner.getUrl5() : url5);

        if ("on".equals(status)) {
            // Обработка, когда статус включен
            lastBanner.setStatus("on");
        } else {
            // Обработка, когда статус выключен или отсутствует
            lastBanner.setStatus("off");
        }

        if (!upload1.isEmpty()) {
            lastBanner.setImagePath1(saveImage(upload1));
        }

        if (!upload2.isEmpty()) {
            lastBanner.setImagePath2(saveImage(upload2));
        }

        if (!upload3.isEmpty()) {
            lastBanner.setImagePath3(saveImage(upload3));
        }

        if (!upload4.isEmpty()) {
            lastBanner.setImagePath4(saveImage(upload4));
        }

        if (!upload5.isEmpty()) {
            lastBanner.setImagePath5(saveImage(upload5));
        }

        lastBanner.setSpeed(rotation_speed);

        // Сохранение обновленного баннера в базу данных
        lastBannerService.saveLastBanner(lastBanner);

        // Перенаправление с атрибутом успеха
        redirectAttributes.addFlashAttribute("successMessage", "Баннеры успешно добавлены.");

        return "redirect:/admin/banners";
    }


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
