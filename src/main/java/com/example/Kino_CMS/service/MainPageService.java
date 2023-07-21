package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.MainPage;
import com.example.Kino_CMS.entity.Promotions;
import com.example.Kino_CMS.repository.MainPageRepository;
import org.springframework.stereotype.Service;

@Service
public class MainPageService {
    private final MainPageRepository mainPageRepository;


    public MainPageService(MainPageRepository mainPageRepository) {
        this.mainPageRepository = mainPageRepository;
    }

    public Iterable<MainPage> getAllMainPages() {
        return mainPageRepository.findAll();
    }

    public MainPage getMainPageById(Long main_page_id) {
        return mainPageRepository.findById(main_page_id).orElse(null);
    }
}
