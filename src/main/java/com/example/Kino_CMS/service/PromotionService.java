package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.Promotions;
import com.example.Kino_CMS.repository.PromotionRepository;
import org.springframework.stereotype.Service;

@Service
public class PromotionService {
    private final PromotionRepository promotionRepository;

    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public Iterable<Promotions> getAllPromotions() {
        return promotionRepository.findAll();
    }
}
