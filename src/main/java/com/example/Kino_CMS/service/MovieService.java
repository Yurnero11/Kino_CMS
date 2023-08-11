package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.Gallary;
import com.example.Kino_CMS.entity.Movies;

import java.util.List;

public interface MovieService {
    Iterable<Movies> getAllMovies();
    Gallary getGalleryByMovieId(Long movieId);
    List<Gallary> getAllGalleries();

    Movies saveMovies(Movies movies);
}
