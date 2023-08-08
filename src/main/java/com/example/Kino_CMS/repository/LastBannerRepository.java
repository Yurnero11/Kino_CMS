package com.example.Kino_CMS.repository;
import com.example.Kino_CMS.entity.BackgroundBanner;
import com.example.Kino_CMS.entity.LastBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface LastBannerRepository extends JpaRepository<LastBanner, Long> {
}
