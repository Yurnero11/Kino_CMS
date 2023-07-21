package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.AboutCinema;
import com.example.Kino_CMS.entity.CafeBar;
import com.example.Kino_CMS.repository.CafeBarRepository;
import org.springframework.stereotype.Service;

@Service
public class CafeBarService {
    private final CafeBarRepository cafeBarRepository;

    public CafeBarService(CafeBarRepository cafeBarRepository) {
        this.cafeBarRepository = cafeBarRepository;
    }

    public CafeBar getCafeBarById(Long cafe_id) {
        return cafeBarRepository.findById(cafe_id).orElse(null);
    }
}
