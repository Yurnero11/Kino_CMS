package com.example.Kino_CMS.repository;

import com.example.Kino_CMS.entity.News;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepository extends CrudRepository<News, Long> {
}
