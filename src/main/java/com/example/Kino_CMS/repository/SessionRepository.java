package com.example.Kino_CMS.repository;

import com.example.Kino_CMS.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findByDay(String day);
}
