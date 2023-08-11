package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.CinemaContacts;
import com.example.Kino_CMS.repository.CinemaContactsRepository;
import com.example.Kino_CMS.service.CinemaContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CinemaContactsServiceImpl implements CinemaContactService {
    private static final Logger log = LogManager.getLogger(CinemaContactsServiceImpl.class);

    @Autowired
    private CinemaContactsRepository contactsRepository;

    @Override
    public CinemaContacts saveCinemaContact(CinemaContacts cinemaContacts) {
        try {
            log.info("Saving CinemaContact: {}", cinemaContacts);
            CinemaContacts result = contactsRepository.save(cinemaContacts);
            log.info("Successfully saved CinemaContact: {}", cinemaContacts);
            return result;
        } catch (Exception e) {
            log.error("Error while saving CinemaContact: {}", cinemaContacts, e);
            throw e;
        }
    }

    @Override
    public Iterable<CinemaContacts> getAllCinemaContact() {
        try {
            log.info("Getting all CinemaContacts");
            Iterable<CinemaContacts> result = contactsRepository.findAll();
            log.info("Successfully retrieved all CinemaContacts");
            return result;
        } catch (Exception e) {
            log.error("Error while getting all CinemaContacts", e);
            throw e;
        }
    }

    @Override
    public Optional<CinemaContacts> getCinemaContactById(Long contact_id) {
        try {
            log.info("Getting CinemaContact by ID: {}", contact_id);
            Optional<CinemaContacts> result = contactsRepository.findById(contact_id);
            if (result.isPresent()) {
                log.info("Successfully retrieved CinemaContact by ID: {}", contact_id);
            } else {
                log.info("CinemaContact with ID {} not found", contact_id);
            }
            return result;
        } catch (Exception e) {
            log.error("Error while getting CinemaContact by ID: {}", contact_id, e);
            throw e;
        }
    }

    @Override
    public void delete(CinemaContacts cinemaContacts) {
        try {
            log.info("Deleting CinemaContact: {}", cinemaContacts);
            contactsRepository.delete(cinemaContacts);
            log.info("Successfully deleted CinemaContact: {}", cinemaContacts);
        } catch (Exception e) {
            log.error("Error while deleting CinemaContact: {}", cinemaContacts, e);
            throw e;
        }
    }
}
