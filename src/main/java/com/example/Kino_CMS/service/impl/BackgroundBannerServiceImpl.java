package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.BackgroundBanner;
import com.example.Kino_CMS.entity.MainBanners;
import com.example.Kino_CMS.repository.BackgroundBannerRepository;
import com.example.Kino_CMS.service.BackgroundBannerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BackgroundBannerServiceImpl implements BackgroundBannerService {
    private static final Logger log = LogManager.getLogger(BackgroundBannerServiceImpl.class);

    @Autowired
    private BackgroundBannerRepository backgroundBannerRepository;

    @Override
    public BackgroundBanner saveBackgroundBanner(BackgroundBanner backgroundBanner) {
        try {
            log.info("Saving BackgroundBanner: {}", backgroundBanner);
            BackgroundBanner result = backgroundBannerRepository.save(backgroundBanner);
            log.info("Successfully saved BackgroundBanner: {}", backgroundBanner);
            return result;
        } catch (Exception e) {
            log.error("Error while saving BackgroundBanner: {}", backgroundBanner, e);
            throw e;
        }
    }

    @Override
    public BackgroundBanner getBackgroundBanner() {
        try {
            // Попробуем получить существующий объект MainBanners из базы данных
            Optional<BackgroundBanner> existingMainBanners = backgroundBannerRepository.findById(17L);

            if (existingMainBanners.isPresent()) {
                BackgroundBanner backgroundBanner = existingMainBanners.get();
                log.info("BackgroundBanner retrieved successfully: {}", backgroundBanner);
                return backgroundBanner;
            } else {
                log.info("BackgroundBanner not found in the database, creating a new one.");
                return new BackgroundBanner();
            }
        } catch (Exception e) {
            log.error("Error while retrieving BackgroundBanner: {}", e.getMessage());
            throw e; // Можно обработать иначе в зависимости от вашей логики
        }
    }
}
