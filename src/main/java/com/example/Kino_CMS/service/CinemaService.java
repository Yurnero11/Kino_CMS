package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.Cinemas;
import com.example.Kino_CMS.entity.Gallary;

import java.util.Optional;

public interface CinemaService {
    Iterable<Cinemas> getAllCinemas();
    Gallary getGalleryByCinemaId(Long cinemaID);

    Cinemas saveCinemas(Cinemas cinemas);

    void delete(Cinemas cinemas);

    Optional<Cinemas> getCinemaById(Long cinema_id);


}
