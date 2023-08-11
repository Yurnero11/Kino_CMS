package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.MainBanners;
import com.example.Kino_CMS.repository.MainBannersRepository;
import com.example.Kino_CMS.service.MainBannerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainBannerServiceImpl implements MainBannerService {
    private static final Logger log = LogManager.getLogger(MainBannerServiceImpl.class);

    @Autowired
    private MainBannersRepository mainBannerRepository;

    @Override
    public MainBanners getLatestMainBanner() {
        try {
            log.info("Getting latest MainBanner");
            MainBanners result = mainBannerRepository.findFirstByOrderByBannerIdDesc();
            log.info("Successfully retrieved latest MainBanner");
            return result;
        } catch (Exception e) {
            log.error("Error while getting latest MainBanner", e);
            throw e;
        }
    }

    @Override
    public Iterable<MainBanners> getAllMainBanners() {
        try {
            log.info("Getting all MainBanners");
            Iterable<MainBanners> result = mainBannerRepository.findAll();
            log.info("Successfully retrieved all MainBanners");
            return result;
        } catch (Exception e) {
            log.error("Error while getting all MainBanners", e);
            throw e;
        }
    }

    @Override
    public MainBanners saveMainBanners(MainBanners mainBanners) {
        try {
            log.info("Saving MainBanners: {}", mainBanners);
            MainBanners result = mainBannerRepository.save(mainBanners);
            log.info("Successfully saved MainBanners: {}", mainBanners);
            return result;
        } catch (Exception e) {
            log.error("Error while saving MainBanners: {}", mainBanners, e);
            throw e;
        }
    }
}
