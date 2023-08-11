package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.News;
import com.example.Kino_CMS.repository.NewsRepository;
import com.example.Kino_CMS.service.NewsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {
    private static final Logger log = LogManager.getLogger(NewsServiceImpl.class);

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public Iterable<News> getAllNews() {
        try {
            log.info("Getting all News");
            Iterable<News> result = newsRepository.findAll();
            log.info("Successfully retrieved all News");
            return result;
        } catch (Exception e) {
            log.error("Error while getting all News", e);
            throw e;
        }
    }

    @Override
    public News saveNews(News news) {
        try {
            log.info("Saving News: {}", news);
            News result = newsRepository.save(news);
            log.info("Successfully saved News: {}", news);
            return result;
        } catch (Exception e) {
            log.error("Error while saving News: {}", news, e);
            throw e;
        }
    }

    @Override
    public void delete(News news) {
        try {
            log.info("Deleting News: {}", news);
            newsRepository.delete(news);
            log.info("Successfully deleted News: {}", news);
        } catch (Exception e) {
            log.error("Error while deleting News: {}", news, e);
            throw e;
        }
    }

    @Override
    public Optional<News> getNewsById(Long newsId) {
        try {
            log.info("Getting News by ID: {}", newsId);
            Optional<News> result = newsRepository.findById(newsId);
            if (result.isPresent()) {
                log.info("Successfully retrieved News by ID: {}", newsId);
            } else {
                log.info("News with ID {} not found", newsId);
            }
            return result;
        } catch (Exception e) {
            log.error("Error while getting News by ID: {}", newsId, e);
            throw e;
        }
    }
}
