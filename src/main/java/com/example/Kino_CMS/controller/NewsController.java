package com.example.Kino_CMS.controller;

import com.example.Kino_CMS.entity.Gallary;
import com.example.Kino_CMS.entity.News;
import com.example.Kino_CMS.entity.SeoBlocks;
import com.example.Kino_CMS.repository.GalleryRepository;
import com.example.Kino_CMS.repository.NewsRepository;
import com.example.Kino_CMS.repository.SeoBlocksRepository;
import com.example.Kino_CMS.service.NewsService;
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
public class NewsController {
    private final NewsService newsService;
    private final GalleryRepository galleryRepository;
    private final SeoBlocksRepository seoBlocksRepository;
    private final NewsRepository newsRepository;

    public NewsController(NewsService newsService, GalleryRepository galleryRepository, SeoBlocksRepository seoBlocksRepository, NewsRepository newsRepository) {
        this.newsService = newsService;
        this.galleryRepository = galleryRepository;
        this.seoBlocksRepository = seoBlocksRepository;
        this.newsRepository = newsRepository;
    }

    @GetMapping("/news")
    public String news_add(Model model){
        Iterable<News> new_s = newsService.getAllNews(); // Здесь используйте ваш сервис или репозиторий для получения списка кинотеатров
        model.addAttribute("new_s", new_s);
        return "/news/news-page";
    }

    @GetMapping("/news/news-add")
    public String newsAdd(Model model){
        return "/news/news-add";
    }

    @PostMapping("/news/news-add")
    public String news_add(
            @RequestParam("news_name") String news_name,
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
        News news = new News();


        news.setNews_title(news_name);
        news.setNews_description(description);
        news.setDate_publication(publication_date);
        news.setMain_image_path(saveImage(main_photo));
        news.setStatus(status);
        gallary.setImagePath1(saveImage(upload1));
        gallary.setImagePath2(saveImage(upload2));
        gallary.setImagePath3(saveImage(upload3));
        gallary.setImagePath4(saveImage(upload4));
        gallary.setImagePath5(saveImage(upload5));
        seoBlocks.setUrl(url);
        seoBlocks.setTitle(title);
        seoBlocks.setKeywords(keywords);
        seoBlocks.setDescription(descriptionSeo);

        news.setGallery(gallary);
        news.setSeoBlocks(seoBlocks);

        newsRepository.save(news);
        galleryRepository.save(gallary);
        seoBlocksRepository.save(seoBlocks);

        return "redirect:/news";
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
