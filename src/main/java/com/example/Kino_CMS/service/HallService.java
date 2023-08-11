package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.Halls;

import java.util.Optional;

public interface HallService {
    Optional<Halls> getGalleryByHallId(Long hallId);

    Halls saveHalls(Halls halls);
}
