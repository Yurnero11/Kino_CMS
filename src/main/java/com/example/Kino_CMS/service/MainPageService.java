package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.MainPage;

public interface MainPageService {
    Iterable<MainPage> getAllMainPages();
    MainPage getMainPageById(Long main_page_id);

    MainPage saveMainPage(MainPage mainPage);
}
