package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.BackgroundBanner;
import com.example.Kino_CMS.repository.BackgroundBannerRepository;
import com.example.Kino_CMS.service.BackgroundBannerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
