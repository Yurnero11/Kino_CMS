package com.example.Kino_CMS.repository;

import com.example.Kino_CMS.entity.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadsRepository extends JpaRepository<FileUpload, Long> {
    FileUpload findByOriginalFileName(String originalFileName);
}
