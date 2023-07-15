package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.Movies;
import com.example.Kino_CMS.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Iterable<Movies> getAllMovies() {
        return movieRepository.findAll();
    }
}
