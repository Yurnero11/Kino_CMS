package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.FileUploads;

import java.util.List;

public interface FileUploadsService {
    Iterable<FileUploads> getAllFiles();
    void saveFile(FileUploads fileUploads);
    // Другие необходимые методы для работы с файлами
    public void deleteFile(Long fileId);
}
