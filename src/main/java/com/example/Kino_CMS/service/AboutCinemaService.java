package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.AboutCinema;
import com.example.Kino_CMS.entity.MainPage;
import com.example.Kino_CMS.repository.AboutCinemaRepository;
import org.springframework.stereotype.Service;

@Service
public class AboutCinemaService {
    private final AboutCinemaRepository aboutCinemaRepository;


    public AboutCinemaService(AboutCinemaRepository aboutCinemaRepository) {
        this.aboutCinemaRepository = aboutCinemaRepository;
    }

    public AboutCinema getAboutCinemaById(Long about_cinema_id) {
        return aboutCinemaRepository.findById(about_cinema_id).orElse(null);
    }
}
