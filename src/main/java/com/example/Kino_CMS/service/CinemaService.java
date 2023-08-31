package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.Cinema;
import com.example.Kino_CMS.entity.Gallary;

import java.util.Optional;

public interface CinemaService {
    Iterable<Cinema> getAllCinemas();
    Gallary getGalleryByCinemaId(Long cinemaID);

    Cinema saveCinemas(Cinema cinema);

    void delete(Cinema cinema);

    Optional<Cinema> getCinemaById(Long cinema_id);


}
