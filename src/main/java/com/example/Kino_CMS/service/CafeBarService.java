package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.AboutCinema;
import com.example.Kino_CMS.entity.CafeBar;

public interface CafeBarService {
    public CafeBar getCafeBarById(Long cafe_id);
    CafeBar saveCafeBar(CafeBar cafeBar);
}
