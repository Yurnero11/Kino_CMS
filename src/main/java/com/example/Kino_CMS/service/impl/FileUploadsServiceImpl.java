package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.FileUploads;
import com.example.Kino_CMS.repository.FileUploadsRepository;
import com.example.Kino_CMS.service.FileUploadsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FileUploadsServiceImpl implements FileUploadsService {
    private final FileUploadsRepository fileUploadsRepository;

    public FileUploadsServiceImpl(FileUploadsRepository fileUploadsRepository) {
        this.fileUploadsRepository = fileUploadsRepository;
    }


    @Override
    public Iterable<FileUploads> getAllFiles() {
        return fileUploadsRepository.findAll();
    }

    @Override
    public void saveFile(FileUploads fileUploads) {
        fileUploadsRepository.save(fileUploads);
    }

    public FileUploads getFileById(Long id) {
        Optional<FileUploads> fileOptional = fileUploadsRepository.findById(id);
        return fileOptional.orElse(null);
    }

    public FileUploads getFileByOriginalFileName(String originalFileName) {
        return fileUploadsRepository.findByOriginalFileName(originalFileName);
    }

    @Override
    public  void deleteFile(Long fileId){
        fileUploadsRepository.deleteById(fileId);
    }
    // Другие методы сервиса
}
