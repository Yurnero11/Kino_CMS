package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.Gallary;
import com.example.Kino_CMS.entity.Halls;
import com.example.Kino_CMS.repository.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HallServiceImpl {
    private final HallRepository hallRepository;

    @Autowired
    public HallServiceImpl(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    public Gallary getGalleryByHallId(Long hallId) {
        Halls halls = hallRepository.findById(hallId).orElse(null);
        if (halls != null) {
            Gallary gallery = halls.getGallery();
            if (gallery != null) {
                return gallery; // Возвращаем объект галереи
            }
        }
        return null;
    }
}
