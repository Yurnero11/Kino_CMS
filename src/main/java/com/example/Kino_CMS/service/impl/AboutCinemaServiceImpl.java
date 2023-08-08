package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.AboutCinema;
import com.example.Kino_CMS.repository.AboutCinemaRepository;
import com.example.Kino_CMS.service.AboutCinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AboutCinemaServiceImpl implements AboutCinemaService {
    @Autowired
    private AboutCinemaRepository aboutCinemaRepository;

    @Override
    public AboutCinema getAboutCinemaById(Long about_cinema_id) {
        return aboutCinemaRepository.findById(about_cinema_id).orElse(new AboutCinema());
    }

    @Override
    public Iterable<AboutCinema> getAllAboutCinema() {
        return aboutCinemaRepository.findAll();
    }

    @Override
    public AboutCinema saveAboutCinema(AboutCinema aboutCinema) {
        return aboutCinemaRepository.save(aboutCinema);
    }
}
