package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.Cinema;
import com.example.Kino_CMS.entity.Gallary;
import com.example.Kino_CMS.repository.CinemaRepository;
import com.example.Kino_CMS.service.impl.CinemaServiceImpl;
import org.apache.logging.log4j.Logger;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CinemaServiceImplTest {
    @Mock
    private CinemaRepository cinemaRepository;

    @InjectMocks
    private CinemaServiceImpl cinemaService;

    @Mock
    private Logger log;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCinemasSuccess() {
        Cinema mockCinema = new Cinema();
        List<Cinema> mockCinemaList = Collections.singletonList(mockCinema);

        when(cinemaRepository.findAll()).thenReturn(mockCinemaList);

        Iterable<Cinema> result = cinemaService.getAllCinemas();

        assertNotNull(result);
        assertEquals(mockCinemaList, result);
    }

    @Test
    public void testGetAllCinemasError() {
        when(cinemaRepository.findAll()).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cinemaService.getAllCinemas();
        });

        assertEquals("Test exception", exception.getMessage());
    }

    @Test
    public void testGetGalleryByCinemaIdSuccess() {
        Long cinemaId = 1L;
        Cinema mockCinema = new Cinema();
        Gallary mockGallery = new Gallary();
        mockCinema.setGallery(mockGallery);

        when(cinemaRepository.findById(cinemaId)).thenReturn(Optional.of(mockCinema));

        Gallary result = cinemaService.getGalleryByCinemaId(cinemaId);

        assertNotNull(result);
        assertEquals(mockGallery, result);
    }

    @Test
    public void testGetGalleryByCinemaIdCinemaNotFound() {
        Long cinemaId = 1L;

        when(cinemaRepository.findById(cinemaId)).thenReturn(Optional.empty());

        Gallary result = cinemaService.getGalleryByCinemaId(cinemaId);

        assertNull(result);
    }

    @Test
    public void testGetGalleryByCinemaIdEmptyGallery() {
        Long cinemaId = 1L;
        Cinema mockCinema = new Cinema();

        when(cinemaRepository.findById(cinemaId)).thenReturn(Optional.of(mockCinema));

        Gallary result = cinemaService.getGalleryByCinemaId(cinemaId);

        assertNull(result);
    }

    @Test
    public void testGetGalleryByCinemaIdError() {
        Long cinemaId = 1L;

        when(cinemaRepository.findById(cinemaId)).thenThrow(new RuntimeException("Test exception"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cinemaService.getGalleryByCinemaId(cinemaId);
        });

        assertEquals("Test exception", exception.getMessage());
    }

    @Test
    public void testGetGalleryByCinemaIdGalleryNotFound() {
        Long cinemaId = 1L;
        Cinema mockCinema = new Cinema();

        when(cinemaRepository.findById(cinemaId)).thenReturn(Optional.of(mockCinema));

        Gallary result = cinemaService.getGalleryByCinemaId(cinemaId);

        assertNull(result);
    }

    @Test
    public void testDeleteCinemasSuccess() {
        Cinema mockCinema = new Cinema();

        cinemaService.delete(mockCinema);

        verify(cinemaRepository, times(1)).delete(mockCinema);
    }

    @Test
    public void testDeleteCinemasError() {
        Cinema mockCinema = new Cinema();

        doThrow(new RuntimeException("Test exception")).when(cinemaRepository).delete(mockCinema);

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cinemaService.delete(mockCinema);
        });

        assertEquals("Test exception", exception.getMessage());

        // Verify that the delete method was called
        verify(cinemaRepository, times(1)).delete(mockCinema);
    }

    @Test
    public void testSaveCinemasSuccess() {
        Cinema mockCinema = new Cinema();

        when(cinemaRepository.save(any(Cinema.class))).thenReturn(mockCinema);

        Cinema result = cinemaService.saveCinemas(mockCinema);

        assertNotNull(result);
        assertEquals(mockCinema, result);

        verify(cinemaRepository, times(1)).save(mockCinema);
    }

    @Test
    public void testSaveCinemasError() {
        Cinema mockCinema = new Cinema();

        when(cinemaRepository.save(eq(mockCinema))).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cinemaService.saveCinemas(mockCinema);
        });

        assertEquals("Test exception", exception.getMessage());

        // Verify that the save method was called
        verify(cinemaRepository, times(1)).save(mockCinema);
    }

    @Test
    public void testGetCinemaByIdExistingCinema() {
        int cinemaId = 1;
        Cinema mockCinema = new Cinema();
        mockCinema.setCinema_id(cinemaId);

        when(cinemaRepository.findById((long) cinemaId)).thenReturn(Optional.of(mockCinema));

        Optional<Cinema> result = cinemaService.getCinemaById((long) cinemaId);

        assertTrue(result.isPresent());
        assertEquals(mockCinema, result.get());

        verify(cinemaRepository, times(1)).findById((long) cinemaId);
    }

    @Test
    public void testGetCinemaByIdNonExistingCinema() {
        Long cinemaId = 1L;

        when(cinemaRepository.findById(cinemaId)).thenReturn(Optional.empty());

        Optional<Cinema> result = cinemaService.getCinemaById(cinemaId);

        assertFalse(result.isPresent());

        verify(cinemaRepository, times(1)).findById(cinemaId);
    }

    @Test(expected = Exception.class)
    public void testGetCinemaByIdException() {
        Long cinemaId = 123L; // замените на реальное значение ID

        // Подготовка тестовых данных (репозиторий выбросит исключение)
        when(cinemaRepository.findById(cinemaId)).thenThrow(new RuntimeException("Test exception"));

        // Выполнение метода
        cinemaService.getCinemaById(cinemaId);

        // Проверка вызовов логирования
        verify(log).info("Getting Cinema by ID: {}", cinemaId);
        verify(log).error("Error while getting Cinema by ID: {}", cinemaId, new RuntimeException("Test exception"));
    }
}
