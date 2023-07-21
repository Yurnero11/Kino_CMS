package com.example.Kino_CMS.repository;

import com.example.Kino_CMS.entity.Count;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailCountRepository extends JpaRepository<Count, Long> {
    @Query("SELECT count.count FROM Count count WHERE count.id = 1")
    int getSentCount();
}
