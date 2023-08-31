package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.BackgroundBanner;
import com.example.Kino_CMS.entity.LastBanner;
import com.example.Kino_CMS.repository.CinemaContactsRepository;
import com.example.Kino_CMS.repository.LastBannerRepository;
import com.example.Kino_CMS.service.impl.CinemaContactsServiceImpl;
import com.example.Kino_CMS.service.impl.LastBannerServiceImpl;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Supplier;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class LastBannerServiceImplTest {
    @Mock
    private LastBannerRepository lastBannerRepository;

    @Mock
    private Logger log; // Мокированный логгер

    @InjectMocks
    private LastBannerServiceImpl lastBannerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveLastBannerSuccess() {
        LastBanner mockLastBanner = new LastBanner();
        when(lastBannerRepository.save(any(LastBanner.class))).thenReturn(mockLastBanner);

        LastBanner result = lastBannerService.saveLastBanner(mockLastBanner);
        assertNotNull(result);
        assertEquals(mockLastBanner, result);

        verify(lastBannerRepository, times(1)).save(mockLastBanner);
    }

    @Test
    public void testSaveLastBannerError() {
        LastBanner mockLastBanner = new LastBanner();
        when(lastBannerRepository.save(eq(mockLastBanner))).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            lastBannerService.saveLastBanner(mockLastBanner);
        });

        assertEquals("Test exception", exception.getMessage());

        // Verify that the save method was called
        verify(lastBannerRepository, times(1)).save(mockLastBanner);
    }

    @Test
    public void testGetExistingLastBanner() {
        LastBanner existingBanner = new LastBanner();
        existingBanner.setBanner_id(5); // Используем метод setId для установки значения

        when(lastBannerRepository.findById(5L)).thenReturn(Optional.of(existingBanner));

        LastBanner result = lastBannerService.getLastBanner();

        assertNotNull(result);
        assertEquals(existingBanner.getBanner_id(), result.getBanner_id()); // Используем метод getId для получения значения

        verify(lastBannerRepository, times(1)).findById(5L);
    }

    @Test
    public void testGetNonExistingLastBanner() {
        when(lastBannerRepository.findById(5L)).thenReturn(Optional.empty());

        LastBanner result = lastBannerService.getLastBanner();

        assertNotNull(result);
        assertEquals(0L, result.getBanner_id()); // Используем метод getId для получения значения

        verify(lastBannerRepository, times(1)).findById(5L);
    }

    @Test
    public void testGetLastBannerError() {
        when(lastBannerRepository.findById(5L)).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            lastBannerService.getLastBanner();
        });

        assertEquals("Test exception", exception.getMessage());

        verify(lastBannerRepository, times(1)).findById(5L);
    }
}
