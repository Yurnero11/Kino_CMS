package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.Cinemas;
import com.example.Kino_CMS.entity.Gallary;
import com.example.Kino_CMS.repository.CinemaRepository;
import org.springframework.stereotype.Service;

@Service
public class CinemaServiceImpl {
    private final CinemaRepository cinemaRepository;

    public CinemaServiceImpl(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    public Iterable<Cinemas> getAllCinemas() {
        return cinemaRepository.findAll();
    }

    public Cinemas getCinemaById(Long id) {
        return cinemaRepository.findById(id).orElse(null);
    }

    public void saveCinema(Cinemas cinema) {
        cinemaRepository.save(cinema);
    }

    public void deleteCinema(Cinemas cinema) {
        cinemaRepository.delete(cinema);
    }

    public Gallary getGalleryByCinemaId(Long cinemaID) {
        Cinemas cinemas = cinemaRepository.findById(cinemaID).orElse(null);
        if (cinemas != null) {
            Gallary gallery = cinemas.getGallery();
            if (gallery != null) {
                return gallery; // Возвращаем объект галереи
            }
        }
        return null;
    }
}
