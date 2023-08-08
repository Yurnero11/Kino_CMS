package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.News;
import com.example.Kino_CMS.repository.NewsRepository;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl {
    private final NewsRepository newsRepository;

    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public Iterable<News> getAllNews() {
        return newsRepository.findAll();
    }
}
