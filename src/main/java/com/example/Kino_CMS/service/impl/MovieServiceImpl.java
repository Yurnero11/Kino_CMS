package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.Gallary;
import com.example.Kino_CMS.entity.Movies;
import com.example.Kino_CMS.repository.GalleryRepository;
import com.example.Kino_CMS.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl {
    private final MovieRepository movieRepository;
    private final GalleryRepository galleryRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, GalleryRepository galleryRepository) {
        this.movieRepository = movieRepository;
        this.galleryRepository = galleryRepository;
    }

    public Iterable<Movies> getAllMovies() {
        return movieRepository.findAll();
    }

    public Gallary getGalleryByMovieId(Long movieId) {
        Movies movie = movieRepository.findById(movieId).orElse(null);
        if (movie != null) {
            Gallary gallery = movie.getGallery();
            if (gallery != null) {
                return gallery; // Возвращаем объект галереи
            }
        }
        return null;
    }

    public List<Gallary> getAllGalleries() {
        return galleryRepository.findAll();
    }
}
