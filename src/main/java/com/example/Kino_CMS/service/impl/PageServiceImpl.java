package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.AboutCinema;
import com.example.Kino_CMS.entity.Pages;
import com.example.Kino_CMS.repository.PageRepository;
import com.example.Kino_CMS.service.PageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PageServiceImpl implements PageService {
    private static final Logger log = LogManager.getLogger(PageServiceImpl.class);

    @Autowired
    private PageRepository pageRepository;

    @Override
    public Iterable<Pages> getAllPages() {
        try {
            log.info("Getting all Pages");
            Iterable<Pages> result = pageRepository.findAll();
            log.info("Successfully retrieved all Pages");
            return result;
        } catch (Exception e) {
            log.error("Error while getting all Pages", e);
            throw e;
        }
    }

    @Override
    public Pages savePages(Pages pages) {
        try {
            log.info("Saving Pages: {}", pages);
            Pages result = pageRepository.save(pages);
            log.info("Successfully saved Pages: {}", pages);
            return result;
        } catch (Exception e) {
            log.error("Error while saving Pages: {}", pages, e);
            throw e;
        }
    }

    @Override
    public void delete(Pages pages) {
        try {
            log.info("Deleting Pages: {}", pages);
            pageRepository.delete(pages);
            log.info("Successfully deleted Pages: {}", pages);
        } catch (Exception e) {
            log.error("Error while deleting Pages: {}", pages, e);
            throw e;
        }
    }

    @Override
    public Optional<Pages> findById(Long page_id) {
        try {
            log.info("Getting Pages by ID: {}", page_id);
            Optional<Pages> result = pageRepository.findById(page_id);
            if (result.isPresent()) {
                log.info("Successfully retrieved Pages by ID: {}", page_id);
            } else {
                log.info("Pages with ID {} not found", page_id);
            }
            return result;
        } catch (Exception e) {
            log.error("Error while getting Pages by ID: {}", page_id, e);
            throw e;
        }
    }
}