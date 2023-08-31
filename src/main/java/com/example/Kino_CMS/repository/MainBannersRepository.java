package com.example.Kino_CMS.repository;

import com.example.Kino_CMS.entity.MainBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainBannersRepository extends JpaRepository<MainBanner, Integer> {
    MainBanner findFirstByOrderByBannerIdDesc();
}
