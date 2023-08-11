package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.Promotions;

import java.util.Optional;

public interface PromotionsService {
    Iterable<Promotions> getAllPromotions();
    Promotions savePromotions(Promotions promotions);
    Optional<Promotions> getPromotionById(Long promotion_id);
    void delete(Promotions promotions);
}
