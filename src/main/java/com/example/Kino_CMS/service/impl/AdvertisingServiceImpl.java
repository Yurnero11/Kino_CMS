package com.example.Kino_CMS.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.example.Kino_CMS.entity.Advertising;
import com.example.Kino_CMS.repository.AdvertisingRepository;
import com.example.Kino_CMS.service.AdvertisingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdvertisingServiceImpl implements AdvertisingService {
    private static final Logger log = LogManager.getLogger(AdvertisingServiceImpl.class);

    @Autowired
    private AdvertisingRepository advertisingRepository;

    @Override
    public Iterable<Advertising> getAllAdvertising() {
        log.info("Getting all Advertising");
        Iterable<Advertising> result = advertisingRepository.findAll();
        log.info("Successfully retrieved all Advertising");
        return result;
    }

    public Optional<Advertising> getAdvertisingById(Long advertising_id) {
        try {
            log.info("Getting Advertising by ID: {}", advertising_id);
            Optional<Advertising> result = advertisingRepository.findById(advertising_id);
            if (result.isPresent()) {
                log.info("Successfully retrieved Advertising by ID: {}", advertising_id);
            } else {
                log.info("Advertising with ID {} not found", advertising_id);
            }
            return result;
        } catch (Exception e) {
            log.error("Error while getting Advertising by ID: {}", advertising_id, e);
            throw e;
        }
    }
}