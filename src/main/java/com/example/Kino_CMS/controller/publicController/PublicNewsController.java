package com.example.Kino_CMS.controller.publicController;

import com.example.Kino_CMS.entity.News;
import com.example.Kino_CMS.repository.NewsRepository;
import com.example.Kino_CMS.service.impl.NewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class PublicNewsController {
    @Autowired
    private NewsServiceImpl newsServiceImpl;

    @GetMapping("/news")
    public String news(@RequestParam(name = "page", defaultValue = "0") int page,
                       @RequestParam(name = "query", required = false) String query,
                       Model model) {

        int pageSize = 2; // Устанавливаем количество новостей на страницу
        Page<News> newsPage = newsServiceImpl.findAllPage(page, pageSize);
        List<News> news = newsPage.getContent();

        model.addAttribute("news", news);
        model.addAttribute("currentPage", page);

        int totalActiveNews = newsServiceImpl.countActiveNews(); // Метод для подсчета активных новостей
        int totalPages = (int) Math.ceil((double) totalActiveNews / pageSize); // Вычисляем общее количество страниц

        model.addAttribute("totalPages", totalPages);

        // Проверяем, есть ли достаточно активных новостей для отображения пагинации
        boolean showPagination = totalActiveNews >= 3;
        model.addAttribute("showPagination", showPagination);

        int nextPage = page + 1;

        // Проверяем, если nextPage больше или равно общему количеству страниц, то nextPage устанавливаем в -1
        if (nextPage >= totalPages) {
            nextPage = -1;
        }

        model.addAttribute("nextPage", nextPage);

        return "/public/news/news";
    }

    @GetMapping("/news/{news_id}")
    public String newsPage(@PathVariable(value = "news_id") long id,
                                 Model model){
        Optional<News> optionalNews = newsServiceImpl.getNewsById(id);
        if (optionalNews.isEmpty()) {
            return "redirect:/news";
        }
        News news = optionalNews.get();

        model.addAttribute("news", news);
        return "/public/news/news-page";
    }
}
