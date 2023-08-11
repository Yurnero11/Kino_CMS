package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.CafeBar;
import com.example.Kino_CMS.repository.CafeBarRepository;
import com.example.Kino_CMS.service.CafeBarService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CafeBarServiceImpl implements CafeBarService {
    private static final Logger log = LogManager.getLogger(CafeBarServiceImpl.class);

    @Autowired
    private CafeBarRepository cafeBarRepository;

    @Override
    public CafeBar getCafeBarById(Long cafe_id) {
        try {
            log.info("Getting CafeBar by ID: {}", cafe_id);
            CafeBar result = cafeBarRepository.findById(cafe_id).orElse(new CafeBar());
            log.info("Successfully retrieved CafeBar by ID: {}", cafe_id);
            return result;
        } catch (Exception e) {
            log.error("Error while getting CafeBar by ID: {}", cafe_id, e);
            throw e;
        }
    }

    public Iterable<CafeBar> getAllCafeBars() {
        try {
            log.info("Getting all CafeBars");
            Iterable<CafeBar> result = cafeBarRepository.findAll();
            log.info("Successfully retrieved all CafeBars");
            return result;
        } catch (Exception e) {
            log.error("Error while getting all CafeBars", e);
            throw e;
        }
    }

    @Override
    public CafeBar saveCafeBar(CafeBar cafeBar) {
        try {
            log.info("Saving CafeBar: {}", cafeBar);
            CafeBar result = cafeBarRepository.save(cafeBar);
            log.info("Successfully saved CafeBar: {}", cafeBar);
            return result;
        } catch (Exception e) {
            log.error("Error while saving CafeBar: {}", cafeBar, e);
            throw e;
        }
    }
}