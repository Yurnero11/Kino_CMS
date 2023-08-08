package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.Promotions;
import com.example.Kino_CMS.repository.PromotionRepository;
import org.springframework.stereotype.Service;

@Service
public class PromotionServiceImpl {
    private final PromotionRepository promotionRepository;

    public PromotionServiceImpl(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public Iterable<Promotions> getAllPromotions() {
        return promotionRepository.findAll();
    }
}
