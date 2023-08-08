package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.AboutCinema;

public interface AboutCinemaService {
    AboutCinema saveAboutCinema(AboutCinema aboutCinema);
    AboutCinema getAboutCinemaById(Long about_cinema_id);

    Iterable<AboutCinema> getAllAboutCinema();
}
