package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.Gallary;
import com.example.Kino_CMS.entity.Movie;

public interface MovieService {
    Iterable<Movie> getAllMovies();
    Gallary getGalleryByMovieId(Long movieId);

    Movie saveMovies(Movie movie);
}
