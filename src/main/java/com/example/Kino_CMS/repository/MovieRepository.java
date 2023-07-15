package com.example.Kino_CMS.repository;

import com.example.Kino_CMS.entity.MainBanners;
import com.example.Kino_CMS.entity.Movies;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MovieRepository extends CrudRepository<Movies, Long> {
    @Query("SELECT COUNT(m) FROM Movies m WHERE m.movie_data = :movie_data")
    long countMoviesByMovieData(@Param("movie_data") String movieData);
}
