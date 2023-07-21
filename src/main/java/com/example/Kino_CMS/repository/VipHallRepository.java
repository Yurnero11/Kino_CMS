package com.example.Kino_CMS.repository;

import com.example.Kino_CMS.entity.VipHall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VipHallRepository extends JpaRepository<VipHall, Long> {}
