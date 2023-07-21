package com.example.Kino_CMS.repository;

import com.example.Kino_CMS.entity.CafeBar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeBarRepository extends JpaRepository<CafeBar, Long> {}
