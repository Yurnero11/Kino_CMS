package com.example.Kino_CMS.repository;

import com.example.Kino_CMS.entity.AboutMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutMovieRepository extends JpaRepository<AboutMovie, Long> {}
