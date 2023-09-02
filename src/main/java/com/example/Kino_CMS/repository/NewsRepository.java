package com.example.Kino_CMS.repository;

import com.example.Kino_CMS.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface NewsRepository extends CrudRepository<News, Long> {
    Page<News> findAll(Pageable pageable);
    Page<News> findByStatus(String status, Pageable pageable);
    @Query("SELECT COUNT(n) FROM News n WHERE n.status = :status")
    long countByStatus(@Param("status") String status);
}
