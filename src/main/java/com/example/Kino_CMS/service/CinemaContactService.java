package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.CinemaContacts;

import java.util.Optional;

public interface CinemaContactService {
    CinemaContacts saveCinemaContact(CinemaContacts cinemaContacts);
    Iterable<CinemaContacts> getAllCinemaContact();

    Optional<CinemaContacts> getCinemaContactById(Long contact_id);

    void delete(CinemaContacts cinemaContacts);
}
