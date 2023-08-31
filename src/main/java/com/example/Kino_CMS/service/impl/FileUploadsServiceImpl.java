package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.FileUpload;
import com.example.Kino_CMS.repository.FileUploadsRepository;
import com.example.Kino_CMS.service.FileUploadsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FileUploadsServiceImpl implements FileUploadsService {
    private static final Logger log = LogManager.getLogger(FileUploadsServiceImpl.class);

    @Autowired
    private FileUploadsRepository fileUploadsRepository;

    @Override
    public Iterable<FileUpload> getAllFiles() {
        try {
            log.info("Getting all FileUploads");
            Iterable<FileUpload> result = fileUploadsRepository.findAll();
            log.info("Successfully retrieved all FileUploads");
            return result;
        } catch (Exception e) {
            log.error("Error while getting all FileUploads", e);
            throw e;
        }
    }

    @Override
    public void saveFile(FileUpload fileUpload) {
        try {
            log.info("Saving FileUploads: {}", fileUpload);
            fileUploadsRepository.save(fileUpload);
            log.info("Successfully saved FileUploads: {}", fileUpload);
        } catch (Exception e) {
            log.error("Error while saving FileUploads: {}", fileUpload, e);
            throw e;
        }
    }

    public FileUpload getFileById(Long id) {
        try {
            log.info("Getting FileUploads by ID: {}", id);
            Optional<FileUpload> fileOptional = fileUploadsRepository.findById(id);
            if (fileOptional.isPresent()) {
                log.info("Successfully retrieved FileUploads by ID: {}", id);
                return fileOptional.get();
            } else {
                log.info("FileUploads with ID {} not found", id);
                return null;
            }
        } catch (Exception e) {
            log.error("Error while getting FileUploads by ID: {}", id, e);
            throw e;
        }
    }

    public FileUpload getFileByOriginalFileName(String originalFileName) {
        try {
            log.info("Getting FileUploads by originalFileName: {}", originalFileName);
            FileUpload result = fileUploadsRepository.findByOriginalFileName(originalFileName);
            if (result != null) {
                log.info("Successfully retrieved FileUploads by originalFileName: {}", originalFileName);
            } else {
                log.info("FileUploads with originalFileName {} not found", originalFileName);
            }
            return result;
        } catch (Exception e) {
            log.error("Error while getting FileUploads by originalFileName: {}", originalFileName, e);
            throw e;
        }
    }

    @Override
    public void deleteFile(Long fileId) {
        try {
            log.info("Deleting FileUploads by ID: {}", fileId);
            fileUploadsRepository.deleteById(fileId);
            log.info("Successfully deleted FileUploads by ID: {}", fileId);
        } catch (Exception e) {
            log.error("Error while deleting FileUploads by ID: {}", fileId, e);
            throw e;
        }
    }
}
