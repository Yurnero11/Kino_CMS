package com.example.Kino_CMS.repository;

import com.example.Kino_CMS.entity.Cinema;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CinemaRepository extends CrudRepository<Cinema, Long> {
    @Override
    Optional<Cinema> findById(Long aLong);
}
