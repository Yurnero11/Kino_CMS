package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.MainBanners;
import com.example.Kino_CMS.repository.MainBannersRepository;
import com.example.Kino_CMS.service.MainBannersService;

public class MainBannerServiceImpl implements MainBannersService {
    private final MainBannersRepository mainBannersRepository;

    public MainBannerServiceImpl(MainBannersRepository mainBannersRepository) {
        this.mainBannersRepository = mainBannersRepository;
    }

    @Override
    public void createBanner(MainBanners mainBanners) {
        mainBannersRepository.save(mainBanners);
    }
}
