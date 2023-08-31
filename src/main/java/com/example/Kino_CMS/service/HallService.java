package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.Hall;

import java.util.Optional;

public interface HallService {
    Optional<Hall> getGalleryByHallId(Long hallId);

    Hall saveHalls(Hall hall);
}
