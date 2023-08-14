package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.BackgroundBanner;
import com.example.Kino_CMS.entity.LastBanner;
import com.example.Kino_CMS.repository.LastBannerRepository;
import com.example.Kino_CMS.service.LastBannerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LastBannerServiceImpl implements LastBannerService {
    private static final Logger log = LogManager.getLogger(LastBannerServiceImpl.class);

    @Autowired
    private LastBannerRepository lastBannerRepository;

    @Override
    public LastBanner saveLastBanner(LastBanner lastBanner) {
        try {
            log.info("Saving LastBanner: {}", lastBanner);
            LastBanner result = lastBannerRepository.save(lastBanner);
            log.info("Successfully saved LastBanner: {}", lastBanner);
            return result;
        } catch (Exception e) {
            log.error("Error while saving LastBanner: {}", lastBanner, e);
            throw e;
        }
    }

    @Override
    public LastBanner getLastBanner() {
        try {
            // Попробуем получить существующий объект MainBanners из базы данных
            Optional<LastBanner> existingMainBanners = lastBannerRepository.findById(5L);

            if (existingMainBanners.isPresent()) {
                LastBanner lastBanner = existingMainBanners.get();
                log.info("LastBanner retrieved successfully: {}", lastBanner);
                return lastBanner;
            } else {
                log.info("BackgroundBanner not found in the database, creating a new one.");
                return new LastBanner();
            }
        } catch (Exception e) {
            log.error("Error while retrieving LastBanner: {}", e.getMessage());
            throw e; // Можно обработать иначе в зависимости от вашей логики
        }
    }
}