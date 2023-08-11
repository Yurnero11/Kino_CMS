package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.Gallary;
import com.example.Kino_CMS.repository.GalleryRepository;
import com.example.Kino_CMS.service.GallaryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GallaryServiceImpl implements GallaryService {
    private static final Logger log = LogManager.getLogger(GallaryServiceImpl.class);

    @Autowired
    private GalleryRepository galleryRepository;

    @Override
    public Gallary saveGallary(Gallary gallary) {
        try {
            log.info("Saving Gallary: {}", gallary);
            Gallary result = galleryRepository.save(gallary);
            log.info("Successfully saved Gallary: {}", gallary);
            return result;
        } catch (Exception e) {
            log.error("Error while saving Gallary: {}", gallary, e);
            throw e;
        }
    }
}
