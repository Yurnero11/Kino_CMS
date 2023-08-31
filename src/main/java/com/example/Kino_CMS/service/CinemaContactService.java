package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.CinemaContact;

import java.util.Optional;

public interface CinemaContactService {
    CinemaContact saveCinemaContact(CinemaContact cinemaContact);
    Iterable<CinemaContact> getAllCinemaContact();

    Optional<CinemaContact> getCinemaContactById(Long contact_id);

    void delete(CinemaContact cinemaContact);
}
