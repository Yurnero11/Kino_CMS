package com.example.Kino_CMS.repository;

import com.example.Kino_CMS.entity.CinemaContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaContactsRepository extends JpaRepository<CinemaContact, Long> {

}


