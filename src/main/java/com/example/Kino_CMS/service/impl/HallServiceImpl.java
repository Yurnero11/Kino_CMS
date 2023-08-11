package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.Gallary;
import com.example.Kino_CMS.entity.Halls;
import com.example.Kino_CMS.repository.HallRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HallServiceImpl {
    private static final Logger log = LogManager.getLogger(HallServiceImpl.class);

    private final HallRepository hallRepository;

    @Autowired
    public HallServiceImpl(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    public Gallary getGalleryByHallId(Long hallId) {
        try {
            log.info("Getting Gallery by Hall ID: {}", hallId);
            Halls halls = hallRepository.findById(hallId).orElse(null);
            if (halls != null) {
                Gallary gallery = halls.getGallery();
                if (gallery != null) {
                    log.info("Successfully retrieved Gallery by Hall ID: {}", hallId);
                    return gallery;
                }
            }
            log.info("Gallery not found for Hall ID: {}", hallId);
            return null;
        } catch (Exception e) {
            log.error("Error while getting Gallery by Hall ID: {}", hallId, e);
            throw e;
        }
    }

    public Optional<Halls> getHallById(Long hall_id) {
        try {
            log.info("Getting Hall by ID: {}", hall_id);
            Optional<Halls> result = hallRepository.findById(hall_id);
            if (result.isPresent()) {
                log.info("Successfully retrieved Hall by ID: {}", hall_id);
            } else {
                log.info("Hall with ID {} not found", hall_id);
            }
            return result;
        } catch (Exception e) {
            log.error("Error while getting Hall by ID: {}", hall_id, e);
            throw e;
        }
    }
}