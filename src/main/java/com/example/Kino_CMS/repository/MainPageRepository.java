package com.example.Kino_CMS.repository;

import com.example.Kino_CMS.entity.MainPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainPageRepository extends JpaRepository<MainPage, Long> {}
