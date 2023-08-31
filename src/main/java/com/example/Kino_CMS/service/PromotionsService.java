package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.Promotion;

import java.util.Optional;

public interface PromotionsService {
    Iterable<Promotion> getAllPromotions();
    Promotion savePromotions(Promotion promotion);
    Optional<Promotion> getPromotionById(Long promotion_id);
    void delete(Promotion promotion);
}
