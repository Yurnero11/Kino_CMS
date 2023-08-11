package com.example.Kino_CMS.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.example.Kino_CMS.entity.AboutCinema;
import com.example.Kino_CMS.repository.AboutCinemaRepository;
import com.example.Kino_CMS.service.AboutCinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AboutCinemaServiceImpl implements AboutCinemaService {
    private static final Logger log = LogManager.getLogger(AboutCinemaServiceImpl.class);

    @Autowired
    private AboutCinemaRepository aboutCinemaRepository;

    @Override
    public AboutCinema getAboutCinemaById(Long about_cinema_id) {
        try {
            log.info("Getting AboutCinema by ID: {}", about_cinema_id);
            AboutCinema result = aboutCinemaRepository.findById(about_cinema_id).orElse(new AboutCinema());
            log.info("Successfully retrieved AboutCinema by ID: {}", about_cinema_id);
            return result;
        } catch (Exception e) {
            log.error("Error while getting AboutCinema by ID: {}", about_cinema_id, e);
            throw e;
        }
    }

    @Override
    public Iterable<AboutCinema> getAllAboutCinema() {
        try {
            log.info("Getting all AboutCinema");
            Iterable<AboutCinema> result = aboutCinemaRepository.findAll();
            log.info("Successfully retrieved all AboutCinema");
            return result;
        } catch (Exception e) {
            log.error("Error while getting all AboutCinema", e);
            throw e;
        }
    }

    @Override
    public AboutCinema saveAboutCinema(AboutCinema aboutCinema) {
        try {
            log.info("Saving AboutCinema: {}", aboutCinema);
            AboutCinema result = aboutCinemaRepository.save(aboutCinema);
            log.info("Successfully saved AboutCinema: {}", aboutCinema);
            return result;
        } catch (Exception e) {
            log.error("Error while saving AboutCinema: {}", aboutCinema, e);
            throw e;
        }
    }
}