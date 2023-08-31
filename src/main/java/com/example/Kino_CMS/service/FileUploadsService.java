package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.FileUpload;

public interface FileUploadsService {
    Iterable<FileUpload> getAllFiles();
    void saveFile(FileUpload fileUpload);
    // Другие необходимые методы для работы с файлами
    public void deleteFile(Long fileId);
}
