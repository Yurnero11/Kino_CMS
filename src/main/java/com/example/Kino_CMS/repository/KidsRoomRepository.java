package com.example.Kino_CMS.repository;

import com.example.Kino_CMS.entity.KidsRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KidsRoomRepository extends JpaRepository<KidsRoom, Long> {}
