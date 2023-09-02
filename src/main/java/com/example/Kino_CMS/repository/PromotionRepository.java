package com.example.Kino_CMS.repository;

import com.example.Kino_CMS.entity.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PromotionRepository extends CrudRepository<Promotion, Long> {
    Page<Promotion> findAll(Pageable pageable);
    Page<Promotion> findByStatus(String status, Pageable pageable);
    @Query("SELECT COUNT(n) FROM Promotion n WHERE n.status = :status")
    long countByStatus(@Param("status") String status);
}
