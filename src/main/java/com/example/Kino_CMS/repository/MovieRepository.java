package com.example.Kino_CMS.repository;

import com.example.Kino_CMS.entity.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    @Query("SELECT COUNT(m) FROM Movie m WHERE m.movie_data = :movie_data")
    long countMoviesByMovieData(@Param("movie_data") String movieData);

}
