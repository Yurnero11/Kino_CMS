package com.example.Kino_CMS.repository;

import com.example.Kino_CMS.entity.Pages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends JpaRepository<Pages, Long> {}
