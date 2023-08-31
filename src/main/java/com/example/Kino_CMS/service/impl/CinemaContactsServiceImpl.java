package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.CinemaContact;
import com.example.Kino_CMS.repository.CinemaContactsRepository;
import com.example.Kino_CMS.service.CinemaContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

@Service
public class CinemaContactsServiceImpl implements CinemaContactService {
    private static final Logger log = LogManager.getLogger(CinemaContactsServiceImpl.class);

    @Autowired
    private CinemaContactsRepository contactsRepository;

    @Override
    public CinemaContact saveCinemaContact(CinemaContact cinemaContact) {
        try {
            log.info("Saving CinemaContact: {}", cinemaContact);
            CinemaContact result = contactsRepository.save(cinemaContact);
            log.info("Successfully saved CinemaContact: {}", cinemaContact);
            return result;
        } catch (Exception e) {
            log.error("Error while saving CinemaContact: {}", cinemaContact, e);
            throw e;
        }
    }

    @Override
    public Iterable<CinemaContact> getAllCinemaContact() {
        try {
            log.info("Getting all CinemaContacts");
            Iterable<CinemaContact> result = contactsRepository.findAll();
            log.info("Successfully retrieved all CinemaContacts");
            return result;
        } catch (Exception e) {
            log.error("Error while getting all CinemaContacts", e);
            throw e;
        }
    }

    @Override
    public Optional<CinemaContact> getCinemaContactById(Long contact_id) {
        try {
            log.info("Getting CinemaContact by ID: {}", contact_id);
            Optional<CinemaContact> result = contactsRepository.findById(contact_id);
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
    public void delete(CinemaContact cinemaContact) {
        try {
            log.info("Deleting CinemaContact: {}", cinemaContact);
            contactsRepository.delete(cinemaContact);
            log.info("Successfully deleted CinemaContact: {}", cinemaContact);
        } catch (Exception e) {
            log.error("Error while deleting CinemaContact: {}", cinemaContact, e);
            throw e;
        }
    }
}
