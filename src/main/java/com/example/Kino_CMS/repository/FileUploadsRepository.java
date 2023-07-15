package com.example.Kino_CMS.repository;

import com.example.Kino_CMS.entity.FileUploads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileUploadsRepository extends JpaRepository<FileUploads, Long> {
    FileUploads findByOriginalFileName(String originalFileName);
}
