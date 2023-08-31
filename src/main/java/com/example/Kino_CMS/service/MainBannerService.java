package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.MainBanner;

public interface MainBannerService {
    MainBanner getMainBanners();
    MainBanner getLatestMainBanner();

    MainBanner saveMainBanners(MainBanner mainBanner);

    Iterable<MainBanner> getAllMainBanners();

}
