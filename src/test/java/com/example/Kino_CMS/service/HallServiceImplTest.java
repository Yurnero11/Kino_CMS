package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.Gallary;
import com.example.Kino_CMS.entity.Hall;
import com.example.Kino_CMS.repository.HallRepository;
import com.example.Kino_CMS.service.impl.HallServiceImpl;
import org.apache.logging.log4j.Logger;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class HallServiceImplTest {
    @Mock
    private HallRepository hallRepository;

    @InjectMocks
    private HallServiceImpl hallService;

    @Mock
    private Logger log;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetGalleryByHallIdSuccess() {
        Long hallId = 1L;
        Hall mockHall = new Hall();
        Gallary mockGallery = new Gallary();
        mockHall.setGallery(mockGallery);

        when(hallRepository.findById(hallId)).thenReturn(Optional.of(mockHall));

        Gallary result = hallService.getGalleryByHallId(hallId);

        assertNotNull(result);
        assertEquals(mockGallery, result);
    }

    @Test
    public void testGetGalleryByHallIdHallNotFound() {
        Long hallId = 1L;

        when(hallRepository.findById(hallId)).thenReturn(Optional.empty());

        Gallary result = hallService.getGalleryByHallId(hallId);

        assertNull(result);
    }

    @Test
    public void testGetGalleryByHallIdGalleryNotFound() {
        Long hallId = 1L;
        Hall mockHall = new Hall();

        when(hallRepository.findById(hallId)).thenReturn(Optional.of(mockHall));

        Gallary result = hallService.getGalleryByHallId(hallId);

        assertNull(result);
    }

    @Test
    public void testGetGalleryByHallIdError() {
        Long hallId = 1L;

        when(hallRepository.findById(hallId)).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            hallService.getGalleryByHallId(hallId);
        });

        assertEquals("Test exception", exception.getMessage());
    }

    @Test
    public void testGetHallByIdExistingHall() {
        int hallId = 1;
        Hall mockHall = new Hall();
        mockHall.setHall_id(hallId);

        when(hallRepository.findById((long) hallId)).thenReturn(Optional.of(mockHall));

        Optional<Hall> result = hallService.getHallById((long) hallId);

        assertTrue(result.isPresent());
        assertEquals(mockHall, result.get());
    }

    @Test
    public void testGetHallByIdNonExistingHall() {
        Long hallId = 1L;

        when(hallRepository.findById(hallId)).thenReturn(Optional.empty());

        Optional<Hall> result = hallService.getHallById(hallId);

        assertFalse(result.isPresent());
    }

    @Test(expected = Exception.class)
    public void testGetHallByIdException() {
        Long hallId = 456L; // замените на реальное значение ID

        // Подготовка тестовых данных (репозиторий выбросит исключение)
        when(hallRepository.findById(hallId)).thenThrow(new RuntimeException("Test exception"));

        // Выполнение метода
        hallService.getHallById(hallId);

        // Проверка вызовов логирования
        verify(log).info("Getting Hall by ID: {}", hallId);
        verify(log).error("Error while getting Hall by ID: {}", hallId, new RuntimeException("Test exception"));
    }
}
