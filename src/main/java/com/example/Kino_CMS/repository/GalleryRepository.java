package com.example.Kino_CMS.repository;

import com.example.Kino_CMS.entity.Gallary;
import com.example.Kino_CMS.entity.MainBanners;
import org.springframework.data.repository.CrudRepository;

public interface GalleryRepository extends CrudRepository<Gallary, Long> {
}
