package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.BackgroundBanner;
import com.example.Kino_CMS.service.impl.BackgroundBannerServiceImpl;
import com.example.Kino_CMS.repository.BackgroundBannerRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class BackgroundBannerServiceImplTest {
    @Mock
    private BackgroundBannerRepository backgroundBannerRepository;

    @InjectMocks
    private BackgroundBannerServiceImpl backgroundBannerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveBackgroundBannerSuccess() {
        BackgroundBanner mockBackgroundBanner = new BackgroundBanner();
        when(backgroundBannerRepository.save(any(BackgroundBanner.class))).thenReturn(mockBackgroundBanner);

        BackgroundBanner result = backgroundBannerService.saveBackgroundBanner(mockBackgroundBanner);
        assertNotNull(result);
        assertEquals(mockBackgroundBanner, result);

        verify(backgroundBannerRepository, times(1)).save(mockBackgroundBanner);
    }

    @Test
    public void testSaveBackgroundBannerError() {
        BackgroundBanner mockBackgroundBanner = new BackgroundBanner();
        when(backgroundBannerRepository.save(eq(mockBackgroundBanner))).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            backgroundBannerService.saveBackgroundBanner(mockBackgroundBanner);
        });

        assertEquals("Test exception", exception.getMessage());

        // Verify that the save method was called
        verify(backgroundBannerRepository, times(1)).save(mockBackgroundBanner);
    }

    @Test
    public void testGetExistingBackgroundBanner() {
        BackgroundBanner existingBanner = new BackgroundBanner();
        existingBanner.setBanner_id(17);

        when(backgroundBannerRepository.findById(17L)).thenReturn(Optional.of(existingBanner));

        BackgroundBanner result = backgroundBannerService.getBackgroundBanner();

        assertNotNull(result);
        assertEquals(existingBanner.getBanner_id(), result.getBanner_id());

        verify(backgroundBannerRepository, times(1)).findById(17L);
    }

    @Test
    public void testGetNonExistingBackgroundBanner() {
        when(backgroundBannerRepository.findById(17L)).thenReturn(Optional.empty());

        BackgroundBanner result = backgroundBannerService.getBackgroundBanner();

        assertNotNull(result);
        assertEquals(0L, result.getBanner_id());

        verify(backgroundBannerRepository, times(1)).findById(17L);
    }

    @Test
    public void testGetBackgroundBannerError() {
        when(backgroundBannerRepository.findById(17L)).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            backgroundBannerService.getBackgroundBanner();
        });

        assertEquals("Test exception", exception.getMessage());

        verify(backgroundBannerRepository, times(1)).findById(17L);
    }
    @Test
    public void testIsBackgroundCheckedForBanner() {
        BackgroundBanner backgroundBanner = new BackgroundBanner();
        backgroundBanner.setBackground_type("background");

        BackgroundBanner yourService = new BackgroundBanner();
        boolean result = backgroundBannerService.isBackgroundCheckedForBanner(backgroundBanner);

        assertTrue(result);
    }

    @Test
    public void testIsBackgroundCheckedForBannerFalse() {
        BackgroundBanner backgroundBanner = new BackgroundBanner();
        backgroundBanner.setBackground_type("simple");

        boolean result = backgroundBannerService.isBackgroundCheckedForBanner(backgroundBanner);

        assertFalse(result);
    }

    @Test
    public void testIsSimpleCheckedForBanner() {
        BackgroundBanner backgroundBanner = new BackgroundBanner();
        backgroundBanner.setBackground_type("simple");

        boolean result = backgroundBannerService.isSimpleCheckedForBanner(backgroundBanner);

        assertTrue(result);
    }

    @Test
    public void testIsSimpleCheckedForBannerFalse() {
        BackgroundBanner backgroundBanner = new BackgroundBanner();
        backgroundBanner.setBackground_type("background");

        boolean result = backgroundBannerService.isSimpleCheckedForBanner(backgroundBanner);

        assertFalse(result);
    }

}
