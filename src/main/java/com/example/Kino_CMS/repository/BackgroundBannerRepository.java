package com.example.Kino_CMS.repository;
import com.example.Kino_CMS.entity.BackgroundBanner;
import com.example.Kino_CMS.entity.MainBanners;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface BackgroundBannerRepository extends JpaRepository<BackgroundBanner, Long> {
}
