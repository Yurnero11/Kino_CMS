package com.example.Kino_CMS.repository;

import com.example.Kino_CMS.entity.AboutCinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutCinemaRepository extends JpaRepository<AboutCinema, Long> { }
