package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.FileUpload;
import com.example.Kino_CMS.repository.FileUploadsRepository;
import com.example.Kino_CMS.service.impl.FileUploadsServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class FileUploadsServiceImplTest {
    @Mock
    private FileUploadsRepository fileUploadsRepository;

    @InjectMocks
    private FileUploadsServiceImpl fileUploadsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllFilesSuccess() {
        FileUpload mockFileUpload = new FileUpload();
        List<FileUpload> mockFileUploadList = Collections.singletonList(mockFileUpload);

        when(fileUploadsRepository.findAll()).thenReturn(mockFileUploadList);

        Iterable<FileUpload> result = fileUploadsService.getAllFiles();

        assertNotNull(result);
        assertEquals(mockFileUploadList, result);
    }

    @Test
    public void testGetAllFilesError() {
        when(fileUploadsRepository.findAll()).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileUploadsService.getAllFiles();
        });

        assertEquals("Test exception", exception.getMessage());
    }

    @Test
    public void testSaveFileSuccess() {
        FileUpload mockFileUpload = new FileUpload();

        fileUploadsService.saveFile(mockFileUpload);

        verify(fileUploadsRepository, times(1)).save(mockFileUpload);
    }

    @Test
    public void testSaveFileError() {
        FileUpload mockFileUpload = new FileUpload();

        doThrow(new RuntimeException("Test exception")).when(fileUploadsRepository).save(mockFileUpload);

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileUploadsService.saveFile(mockFileUpload);
        });

        assertEquals("Test exception", exception.getMessage());

        // Verify that the save method was called
        verify(fileUploadsRepository, times(1)).save(mockFileUpload);
    }

    @Test
    public void testGetFileByIdExistingFile() {
        int fileId = 1;
        FileUpload mockFileUpload = new FileUpload();
        mockFileUpload.setId(fileId);

        when(fileUploadsRepository.findById((long) fileId)).thenReturn(Optional.of(mockFileUpload));

        FileUpload result = fileUploadsService.getFileById((long) fileId);

        assertNotNull(result);
        assertEquals(mockFileUpload, result);

        verify(fileUploadsRepository, times(1)).findById((long) fileId);
    }

    @Test
    public void testGetFileByIdNonExistingFile() {
        Long fileId = 1L;

        when(fileUploadsRepository.findById(fileId)).thenReturn(Optional.empty());

        FileUpload result = fileUploadsService.getFileById(fileId);

        assertNull(result);

        verify(fileUploadsRepository, times(1)).findById(fileId);
    }

    @Test
    public void testGetFileByIdError() {
        Long fileId = 1L;

        when(fileUploadsRepository.findById(fileId)).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileUploadsService.getFileById(fileId);
        });

        assertEquals("Test exception", exception.getMessage());

        // Verify that the findById method was called
        verify(fileUploadsRepository, times(1)).findById(fileId);
    }

    @Test
    public void testGetFileByOriginalFileNameExistingFile() {
        String originalFileName = "testfile.txt";
        FileUpload mockFileUpload = new FileUpload();
        mockFileUpload.setOriginalFileName(originalFileName);

        when(fileUploadsRepository.findByOriginalFileName(originalFileName)).thenReturn(mockFileUpload);

        FileUpload result = fileUploadsService.getFileByOriginalFileName(originalFileName);

        assertNotNull(result);
        assertEquals(mockFileUpload, result);

        verify(fileUploadsRepository, times(1)).findByOriginalFileName(originalFileName);
    }

    @Test
    public void testGetFileByOriginalFileNameNonExistingFile() {
        String originalFileName = "nonexistent.txt";

        when(fileUploadsRepository.findByOriginalFileName(originalFileName)).thenReturn(null);

        FileUpload result = fileUploadsService.getFileByOriginalFileName(originalFileName);

        assertNull(result);

        verify(fileUploadsRepository, times(1)).findByOriginalFileName(originalFileName);
    }

    @Test
    public void testGetFileByOriginalFileNameError() {
        String originalFileName = "testfile.txt";

        when(fileUploadsRepository.findByOriginalFileName(originalFileName)).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileUploadsService.getFileByOriginalFileName(originalFileName);
        });

        assertEquals("Test exception", exception.getMessage());

        // Verify that the findByOriginalFileName method was called
        verify(fileUploadsRepository, times(1)).findByOriginalFileName(originalFileName);
    }

    @Test
    public void testDeleteFileSuccess() {
        Long fileId = 1L;

        fileUploadsService.deleteFile(fileId);

        verify(fileUploadsRepository, times(1)).deleteById(fileId);
    }

    @Test
    public void testDeleteFileError() {
        Long fileId = 1L;

        doThrow(new RuntimeException("Test exception")).when(fileUploadsRepository).deleteById(fileId);

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileUploadsService.deleteFile(fileId);
        });

        assertEquals("Test exception", exception.getMessage());

        // Verify that the deleteById method was called
        verify(fileUploadsRepository, times(1)).deleteById(fileId);
    }

}
