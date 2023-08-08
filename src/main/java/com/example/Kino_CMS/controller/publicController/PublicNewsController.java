package com.example.Kino_CMS.controller.publicController;

import com.example.Kino_CMS.entity.News;
import com.example.Kino_CMS.repository.NewsRepository;
import com.example.Kino_CMS.service.impl.NewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class PublicNewsController {
    private final NewsRepository newsRepository;
    private final NewsServiceImpl newsServiceImpl;

    @Autowired
    public PublicNewsController(NewsRepository newsRepository, NewsServiceImpl newsServiceImpl) {
        this.newsRepository = newsRepository;
        this.newsServiceImpl = newsServiceImpl;
    }

    @GetMapping("/news")
    public String news(Model model){
        Iterable<News> news = newsServiceImpl.getAllNews(); // Здесь используйте ваш сервис или репозиторий для получения списка кинотеатров
        model.addAttribute("news", news);
        return "/public/news/news";
    }

    @GetMapping("/news/{news_id}")
    public String newsPage(@PathVariable(value = "news_id") long id,
                                 Model model){
        Optional<News> optionalNews = newsRepository.findById(id);
        if (optionalNews.isEmpty()) {
            return "redirect:/news";
        }
        News news = optionalNews.get();

        model.addAttribute("news", news);
        return "/public/news/news-page";
    }
}
