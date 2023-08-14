package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.MainBanners;

public interface MainBannerService {
    MainBanners getMainBanners();
    MainBanners getLatestMainBanner();

    MainBanners saveMainBanners(MainBanners mainBanners);

    Iterable<MainBanners> getAllMainBanners();
}
