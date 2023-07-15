package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.Cinemas;
import com.example.Kino_CMS.repository.CinemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaService {
    private final CinemaRepository cinemaRepository;

    public CinemaService(CinemaRepository cinemaRepository) {
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
}
