package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.News;

import java.util.Optional;

public interface NewsService {
    Iterable<News> getAllNews();

    News saveNews(News news);
    void delete(News news);
    Optional<News> getNewsById(Long newsId);
}
