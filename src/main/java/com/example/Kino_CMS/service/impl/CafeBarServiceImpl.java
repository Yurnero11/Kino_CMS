package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.CafeBar;
import com.example.Kino_CMS.repository.CafeBarRepository;
import com.example.Kino_CMS.service.CafeBarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CafeBarServiceImpl implements CafeBarService {
    @Autowired
    private  CafeBarRepository cafeBarRepository;

    @Override
    public CafeBar getCafeBarById(Long cafe_id) {
        return cafeBarRepository.findById(cafe_id).orElse(new CafeBar());
    }

    @Override
    public CafeBar saveCafeBar(CafeBar cafeBar) {
        return cafeBarRepository.save(cafeBar);
    }


}
