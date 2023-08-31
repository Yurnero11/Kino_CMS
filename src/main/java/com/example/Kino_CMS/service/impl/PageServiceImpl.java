package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.Page;
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
    public Iterable<Page> getAllPages() {
        try {
            log.info("Getting all Pages");
            Iterable<Page> result = pageRepository.findAll();
            log.info("Successfully retrieved all Pages");
            return result;
        } catch (Exception e) {
            log.error("Error while getting all Pages", e);
            throw e;
        }
    }

    @Override
    public Page savePages(Page page) {
        try {
            log.info("Saving Pages: {}", page);
            Page result = pageRepository.save(page);
            log.info("Successfully saved Pages: {}", page);
            return result;
        } catch (Exception e) {
            log.error("Error while saving Pages: {}", page, e);
            throw e;
        }
    }

    @Override
    public void delete(Page page) {
        try {
            log.info("Deleting Pages: {}", page);
            pageRepository.delete(page);
            log.info("Successfully deleted Pages: {}", page);
        } catch (Exception e) {
            log.error("Error while deleting Pages: {}", page, e);
            throw e;
        }
    }

    @Override
    public Optional<Page> findById(Long page_id) {
        try {
            log.info("Getting Pages by ID: {}", page_id);
            Optional<Page> result = pageRepository.findById(page_id);
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