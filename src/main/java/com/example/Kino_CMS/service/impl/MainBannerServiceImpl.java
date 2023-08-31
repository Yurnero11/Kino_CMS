package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.MainBanner;
import com.example.Kino_CMS.repository.MainBannersRepository;
import com.example.Kino_CMS.service.MainBannerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MainBannerServiceImpl implements MainBannerService {
    private static final Logger log = LogManager.getLogger(MainBannerServiceImpl.class);

    @Autowired
    private MainBannersRepository mainBannerRepository;

    @Override
    public MainBanner getLatestMainBanner() {
        try {
            log.info("Getting latest MainBanner");
            MainBanner result = mainBannerRepository.findFirstByOrderByBannerIdDesc();
            log.info("Successfully retrieved latest MainBanner");
            return result;
        } catch (Exception e) {
            log.error("Error while getting latest MainBanner", e);
            throw e;
        }
    }

    @Override
    public MainBanner getMainBanners() {
        try {
            // Попробуем получить существующий объект MainBanners из базы данных
            Optional<MainBanner> existingMainBanners = mainBannerRepository.findById(27);

            if (existingMainBanners.isPresent()) {
                MainBanner mainBanner = existingMainBanners.get();
                log.info("MainBanners retrieved successfully: {}", mainBanner);
                return mainBanner;
            } else {
                log.info("MainBanners not found in the database, creating a new one.");
                return new MainBanner();
            }
        } catch (Exception e) {
            log.error("Error while retrieving MainBanners: {}", e.getMessage());
            throw e; // Можно обработать иначе в зависимости от вашей логики
        }
    }



    @Override
    public Iterable<MainBanner> getAllMainBanners() {
        try {
            log.info("Getting all MainBanners");
            Iterable<MainBanner> result = mainBannerRepository.findAll();
            log.info("Successfully retrieved all MainBanners");
            return result;
        } catch (Exception e) {
            log.error("Error while getting all MainBanners", e);
            throw e;
        }
    }


    @Override
    public MainBanner saveMainBanners(MainBanner mainBanner) {
        try {
            log.info("Saving MainBanners: {}", mainBanner);
            MainBanner result = mainBannerRepository.save(mainBanner);
            log.info("Successfully saved MainBanners: {}", mainBanner);
            return result;
        } catch (Exception e) {
            log.error("Error while saving MainBanners: {}", mainBanner, e);
            throw e;
        }
    }
}
