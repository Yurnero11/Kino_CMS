package com.example.Kino_CMS.repository;

import com.example.Kino_CMS.entity.Hall;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HallRepository extends CrudRepository<Hall, Long> {
}
