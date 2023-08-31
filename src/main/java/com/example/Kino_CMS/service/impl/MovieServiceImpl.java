package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.Gallary;
import com.example.Kino_CMS.entity.Movie;
import com.example.Kino_CMS.repository.GalleryRepository;
import com.example.Kino_CMS.repository.MovieRepository;
import com.example.Kino_CMS.service.MovieService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {
    private static final Logger log = LogManager.getLogger(MovieServiceImpl.class);

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GalleryRepository galleryRepository;

    @Override
    public Iterable<Movie> getAllMovies() {
        try {
            log.info("Getting all Movies");
            Iterable<Movie> result = movieRepository.findAll();
            log.info("Successfully retrieved all Movies");
            return result;
        } catch (Exception e) {
            log.error("Error while getting all Movies", e);
            throw e;
        }
    }

    public Optional<Movie> getMovieById(Long movie_id) {
        try {
            log.info("Getting Movie by ID: {}", movie_id);
            Optional<Movie> result = movieRepository.findById(movie_id);
            if (result.isPresent()) {
                log.info("Successfully retrieved Movie by ID: {}", movie_id);
            } else {
                log.info("Movie with ID {} not found", movie_id);
            }
            return result;
        } catch (Exception e) {
            log.error("Error while getting Movie by ID: {}", movie_id, e);
            throw e;
        }
    }

    @Override
    public Gallary getGalleryByMovieId(Long movieId) {
        try {
            log.info("Getting Gallery by Movie ID: {}", movieId);
            Movie movie = movieRepository.findById(movieId).orElse(null);
            if (movie != null) {
                Gallary gallery = movie.getGallery();
                if (gallery != null) {
                    log.info("Successfully retrieved Gallery by Movie ID: {}", movieId);
                    return gallery;
                }
            }
            log.info("Gallery not found for Movie ID: {}", movieId);
            return null;
        } catch (Exception e) {
            log.error("Error while getting Gallery by Movie ID: {}", movieId, e);
            throw e;
        }
    }


    @Override
    public Movie saveMovies(Movie movie) {
        try {
            log.info("Saving Movies: {}", movie);
            Movie result = movieRepository.save(movie);
            log.info("Successfully saved Movies: {}", movie);
            return result;
        } catch (Exception e) {
            log.error("Error while saving Movies: {}", movie, e);
            throw e;
        }
    }
}
