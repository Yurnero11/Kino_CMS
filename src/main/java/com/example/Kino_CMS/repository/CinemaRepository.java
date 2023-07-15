package com.example.Kino_CMS.repository;

import com.example.Kino_CMS.entity.Cinemas;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CinemaRepository extends CrudRepository<Cinemas, Long> {
    @Override
    Optional<Cinemas> findById(Long aLong);
}
