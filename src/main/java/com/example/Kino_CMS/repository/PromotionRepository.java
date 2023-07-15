package com.example.Kino_CMS.repository;

import com.example.Kino_CMS.entity.BackgroundBanner;
import com.example.Kino_CMS.entity.Promotions;
import org.springframework.data.repository.CrudRepository;

public interface PromotionRepository extends CrudRepository<Promotions, Long> {

}
