package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.MainPage;
import com.example.Kino_CMS.repository.MainPageRepository;
import com.example.Kino_CMS.service.MainPageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainPageServiceImpl implements MainPageService {
    private static final Logger log = LogManager.getLogger(MainPageServiceImpl.class);

    @Autowired
    private MainPageRepository mainPageRepository;

    @Override
    public Iterable<MainPage> getAllMainPages() {
        try {
            log.info("Getting all MainPages");
            Iterable<MainPage> result = mainPageRepository.findAll();
            log.info("Successfully retrieved all MainPages");
            return result;
        } catch (Exception e) {
            log.error("Error while getting all MainPages", e);
            throw e;
        }
    }

    @Override
    public MainPage getMainPageById(Long main_page_id) {
        try {
            log.info("Getting MainPage by ID: {}", main_page_id);
            MainPage result = mainPageRepository.findById(main_page_id).orElse(new MainPage());
            log.info("Successfully retrieved MainPage by ID: {}", main_page_id);
            return result;
        } catch (Exception e) {
            log.error("Error while getting MainPage by ID: {}", main_page_id, e);
            throw e;
        }
    }

    @Override
    public MainPage saveMainPage(MainPage mainPage) {
        try {
            log.info("Saving MainPage: {}", mainPage);
            MainPage result = mainPageRepository.save(mainPage);
            log.info("Successfully saved MainPage: {}", mainPage);
            return result;
        } catch (Exception e) {
            log.error("Error while saving MainPage: {}", mainPage, e);
            throw e;
        }
    }
}