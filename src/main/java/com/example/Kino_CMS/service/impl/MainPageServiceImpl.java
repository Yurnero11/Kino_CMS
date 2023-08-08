package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.MainPage;
import com.example.Kino_CMS.repository.MainPageRepository;
import org.springframework.stereotype.Service;

@Service
public class MainPageServiceImpl {
    private final MainPageRepository mainPageRepository;


    public MainPageServiceImpl(MainPageRepository mainPageRepository) {
        this.mainPageRepository = mainPageRepository;
    }

    public Iterable<MainPage> getAllMainPages() {
        return mainPageRepository.findAll();
    }

    public MainPage getMainPageById(Long main_page_id) {
        return mainPageRepository.findById(main_page_id).orElse(null);
    }
}
