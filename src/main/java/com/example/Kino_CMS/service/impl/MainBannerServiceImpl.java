package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.MainBanners;
import com.example.Kino_CMS.repository.MainBannersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainBannerServiceImpl {
    private final MainBannersRepository mainBannerRepository;

    @Autowired
    public MainBannerServiceImpl(MainBannersRepository mainBannerRepository) {
        this.mainBannerRepository = mainBannerRepository;
    }

    public MainBanners getLatestMainBanner() {
        return mainBannerRepository.findFirstByOrderByBannerIdDesc();
    }
}
