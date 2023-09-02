package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.Promotion;
import com.example.Kino_CMS.repository.PromotionRepository;
import com.example.Kino_CMS.service.PromotionsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PromotionServiceImpl implements PromotionsService {
    private static final Logger log = LogManager.getLogger(PromotionServiceImpl.class);

    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public Iterable<Promotion> getAllPromotions() {
        try {
            log.info("Getting all Promotions");
            Iterable<Promotion> result = promotionRepository.findAll();
            log.info("Successfully retrieved all Promotions");
            return result;
        } catch (Exception e) {
            log.error("Error while getting all Promotions", e);
            throw e;
        }
    }

    @Override
    public Optional<Promotion> getPromotionById(Long promotion_id) {
        try {
            log.info("Getting Promotion by ID: {}", promotion_id);
            Optional<Promotion> result = promotionRepository.findById(promotion_id);
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
    public Promotion savePromotions(Promotion promotion) {
        try {
            log.info("Saving Promotions: {}", promotion);
            Promotion result = promotionRepository.save(promotion);
            log.info("Successfully saved Promotions: {}", promotion);
            return result;
        } catch (Exception e) {
            log.error("Error while saving Promotions: {}", promotion, e);
            throw e;
        }
    }

    @Override
    public void delete(Promotion promotion) {
        try {
            log.info("Deleting Promotions: {}", promotion);
            promotionRepository.delete(promotion);
            log.info("Successfully deleted Promotions: {}", promotion);
        } catch (Exception e) {
            log.error("Error while deleting Promotions: {}", promotion, e);
            throw e;
        }
    }

    public Page<Promotion> findAllPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        // Получаем только новости со статусом "on"
        Page<Promotion> newsPage = promotionRepository.findByStatus("on", pageable);

        return newsPage;
    }

    public int countActiveNews() {
        return (int) promotionRepository.countByStatus("on"); // Предположим, что у вас есть метод countByStatus для подсчета активных новостей
    }
}
