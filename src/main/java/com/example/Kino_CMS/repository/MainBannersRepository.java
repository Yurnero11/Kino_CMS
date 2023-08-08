package com.example.Kino_CMS.repository;

import com.example.Kino_CMS.entity.MainBanners;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MainBannersRepository extends JpaRepository<MainBanners, Integer> {
    MainBanners findFirstByOrderByBannerIdDesc();
}
