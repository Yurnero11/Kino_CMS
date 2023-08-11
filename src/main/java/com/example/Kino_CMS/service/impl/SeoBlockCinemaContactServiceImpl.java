package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.CinemaContacts;
import com.example.Kino_CMS.entity.SeoBlockCinemaContact;
import com.example.Kino_CMS.repository.SeoBlockCinemaContactRepository;
import com.example.Kino_CMS.service.SeoBlockCinemaContactService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeoBlockCinemaContactServiceImpl implements SeoBlockCinemaContactService {
    private static final Logger log = LogManager.getLogger(SeoBlockCinemaContactServiceImpl.class);

    @Autowired
    private SeoBlockCinemaContactRepository seoBlockCinemaContactRepository;

    @Override
    public Optional<SeoBlockCinemaContact> getSeoBlockById(Long seo_block_id) {
        try {
            log.info("Getting SeoBlock by ID: {}", seo_block_id);
            Optional<SeoBlockCinemaContact> result = seoBlockCinemaContactRepository.findById(seo_block_id);
            if (result.isPresent()) {
                log.info("Successfully retrieved SeoBlock by ID: {}", seo_block_id);
            } else {
                log.info("SeoBlock with ID {} not found", seo_block_id);
            }
            return result;
        } catch (Exception e) {
            log.error("Error while getting SeoBlock by ID: {}", seo_block_id, e);
            throw e;
        }
    }

    @Override
    public SeoBlockCinemaContact saveSeoBlock(SeoBlockCinemaContact seoBlockCinemaContact) {
        try {
            log.info("Saving SeoBlock: {}", seoBlockCinemaContact);
            SeoBlockCinemaContact result = seoBlockCinemaContactRepository.save(seoBlockCinemaContact);
            log.info("Successfully saved SeoBlock: {}", seoBlockCinemaContact);
            return result;
        } catch (Exception e) {
            log.error("Error while saving SeoBlock: {}", seoBlockCinemaContact, e);
            throw e;
        }
    }
}
