package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.Gallary;
import com.example.Kino_CMS.repository.GalleryRepository;
import com.example.Kino_CMS.service.impl.GallaryServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class GallaryServiceImplTest {
    @Mock
    private GalleryRepository galleryRepository;

    @InjectMocks
    private GallaryServiceImpl galleryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveGallarySuccess() {
        Gallary mockGallary = new Gallary();

        when(galleryRepository.save(any(Gallary.class))).thenReturn(mockGallary);

        Gallary result = galleryService.saveGallary(mockGallary);

        assertNotNull(result);
        assertEquals(mockGallary, result);

        verify(galleryRepository, times(1)).save(mockGallary);
    }

    @Test
    public void testSaveGallaryError() {
        Gallary mockGallary = new Gallary();

        when(galleryRepository.save(eq(mockGallary))).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            galleryService.saveGallary(mockGallary);
        });

        assertEquals("Test exception", exception.getMessage());

        // Verify that the save method was called
        verify(galleryRepository, times(1)).save(mockGallary);
    }
}
