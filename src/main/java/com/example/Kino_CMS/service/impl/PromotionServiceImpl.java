package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.Promotions;
import com.example.Kino_CMS.repository.PromotionRepository;
import com.example.Kino_CMS.service.PromotionsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PromotionServiceImpl implements PromotionsService {
    private static final Logger log = LogManager.getLogger(PromotionServiceImpl.class);

    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public Iterable<Promotions> getAllPromotions() {
        try {
            log.info("Getting all Promotions");
            Iterable<Promotions> result = promotionRepository.findAll();
            log.info("Successfully retrieved all Promotions");
            return result;
        } catch (Exception e) {
            log.error("Error while getting all Promotions", e);
            throw e;
        }
    }

    @Override
    public Optional<Promotions> getPromotionById(Long promotion_id) {
        try {
            log.info("Getting Promotion by ID: {}", promotion_id);
            Optional<Promotions> result = promotionRepository.findById(promotion_id);
            if (result.isPresent()) {
                log.info("Successfully retrieved Promotion by ID: {}", promotion_id);
            } else {
                log.info("Promotion with ID {} not found", promotion_id);
            }
            return result;
        } catch (Exception e) {
            log.error("Error while getting Promotion by ID: {}", promotion_id, e);
            throw e;
        }
    }

    @Override
    public Promotions savePromotions(Promotions promotions) {
        try {
            log.info("Saving Promotions: {}", promotions);
            Promotions result = promotionRepository.save(promotions);
            log.info("Successfully saved Promotions: {}", promotions);
            return result;
        } catch (Exception e) {
            log.error("Error while saving Promotions: {}", promotions, e);
            throw e;
        }
    }

    @Override
    public void delete(Promotions promotions) {
        try {
            log.info("Deleting Promotions: {}", promotions);
            promotionRepository.delete(promotions);
            log.info("Successfully deleted Promotions: {}", promotions);
        } catch (Exception e) {
            log.error("Error while deleting Promotions: {}", promotions, e);
            throw e;
        }
    }
}
