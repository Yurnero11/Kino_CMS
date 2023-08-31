package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.Cinema;
import com.example.Kino_CMS.entity.Gallary;
import com.example.Kino_CMS.repository.CinemaRepository;
import com.example.Kino_CMS.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

@Service
public class CinemaServiceImpl implements CinemaService {
    private static final Logger log = LogManager.getLogger(CinemaServiceImpl.class);

    @Autowired
    private CinemaRepository cinemaRepository;

    @Override
    public Iterable<Cinema> getAllCinemas() {
        try {
            log.info("Getting all Cinemas");
            Iterable<Cinema> result = cinemaRepository.findAll();
            log.info("Successfully retrieved all Cinemas");
            return result;
        } catch (Exception e) {
            log.error("Error while getting all Cinemas", e);
            throw e;
        }
    }

    @Override
    public Gallary getGalleryByCinemaId(Long cinemaID) {
        try {
            log.info("Getting Gallery by Cinema ID: {}", cinemaID);
            Cinema cinema = cinemaRepository.findById(cinemaID).orElse(null);
            if (cinema != null) {
                Gallary gallery = cinema.getGallery();
                if (gallery != null) {
                    log.info("Successfully retrieved Gallery by Cinema ID: {}", cinemaID);
                    return gallery;
                }
            }
            log.info("Gallery not found for Cinema ID: {}", cinemaID);
            return null;
        } catch (Exception e) {
            log.error("Error while getting Gallery by Cinema ID: {}", cinemaID, e);
            throw e;
        }
    }

    @Override
    public void delete(Cinema cinema) {
        try {
            log.info("Deleting Cinemas: {}", cinema);
            cinemaRepository.delete(cinema);
            log.info("Successfully deleted Cinemas: {}", cinema);
        } catch (Exception e) {
            log.error("Error while deleting Cinemas: {}", cinema, e);
            throw e;
        }
    }

    @Override
    public Cinema saveCinemas(Cinema cinema) {
        try {
            log.info("Saving Cinemas: {}", cinema);
            Cinema result = cinemaRepository.save(cinema);
            log.info("Successfully saved Cinemas: {}", cinema);
            return result;
        } catch (Exception e) {
            log.error("Error while saving Cinemas: {}", cinema, e);
            throw e;
        }
    }

    @Override
    public Optional<Cinema> getCinemaById(Long cinema_id) {
        try {
            log.info("Getting Cinema by ID: {}", cinema_id);
            Optional<Cinema> result = cinemaRepository.findById(cinema_id);
            if (result.isPresent()) {
                log.info("Successfully retrieved Cinema by ID: {}", cinema_id);
            } else {
                log.info("Cinema with ID {} not found", cinema_id);
            }
            return result;
        } catch (Exception e) {
            log.error("Error while getting Cinema by ID: {}", cinema_id, e);
            throw e;
        }
    }
}
