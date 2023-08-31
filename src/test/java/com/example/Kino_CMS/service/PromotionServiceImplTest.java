package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.Promotion;
import com.example.Kino_CMS.repository.PromotionRepository;
import com.example.Kino_CMS.service.impl.PromotionServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class PromotionServiceImplTest {
    @Mock
    private PromotionRepository promotionRepository;

    @InjectMocks
    private PromotionServiceImpl promotionService;

    @Mock
    public Logger log;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPromotionsSuccess() {
        Promotion mockCinema = new Promotion();
        List<Promotion> mockCinemaList = Collections.singletonList(mockCinema);

        when(promotionRepository.findAll()).thenReturn(mockCinemaList);

        Iterable<Promotion> result = promotionService.getAllPromotions();

        assertNotNull(result);
        assertEquals(mockCinemaList, result);
    }

    @Test
    public void testGetAllPromotionsError() {
        when(promotionRepository.findAll()).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            promotionService.getAllPromotions();
        });

        assertEquals("Test exception", exception.getMessage());
    }



    @Test
    public void testSaveCinemasSuccess() {
        Promotion promotion = new Promotion();

        when(promotionRepository.save(any(Promotion.class))).thenReturn(promotion);

        Promotion result = promotionService.savePromotions(promotion);

        assertNotNull(result);
        assertEquals(promotion, result);

        //verify(moviesService, times(1)).saveMovies(mockCinema);
    }

    @Test
    public void testSaveCinemasError() {
        Promotion promotion = new Promotion();

        when(promotionRepository.save(eq(promotion))).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            promotionService.savePromotions(promotion);
        });

        assertEquals("Test exception", exception.getMessage());

        // Verify that the save method was called
        verify(promotionRepository, times(1)).save(promotion);
    }

    @Test
    public void testGetCinemaByIdExistingCinema() {
        int cinemaId = 1;
        Promotion promotion = new Promotion();
        promotion.setPromotion_id(cinemaId);

        when(promotionRepository.findById((long) cinemaId)).thenReturn(Optional.of(promotion));

        Optional<Promotion> result = promotionService.getPromotionById((long) cinemaId);

        assertTrue(result.isPresent());
        assertEquals(promotion, result.get());

        verify(promotionRepository, times(1)).findById((long) cinemaId);
    }

    @Test
    public void testGetCinemaByIdNonExistingCinema() {
        Long cinemaId = 1L;

        when(promotionRepository.findById(cinemaId)).thenReturn(Optional.empty());

        Optional<Promotion> result = promotionService.getPromotionById((long) cinemaId);

        assertFalse(result.isPresent());

        verify(promotionRepository, times(1)).findById(cinemaId);
    }

    @Test(expected = Exception.class)
    public void testGetCinemaByIdException() {
        Long cinemaId = 123L; // замените на реальное значение ID

        // Подготовка тестовых данных (репозиторий выбросит исключение)
        when(promotionRepository.findById(cinemaId)).thenThrow(new RuntimeException("Test exception"));

        // Выполнение метода
        promotionService.getPromotionById(cinemaId);

        // Проверка вызовов логирования
        verify(log).info("Getting Movie by ID: {}", cinemaId);
        verify(log).error("Error while getting Movie by ID: {}", cinemaId, new RuntimeException("Test exception"));
    }


    @Test
    public void testDeleteNewsSuccess() {
        // Arrange
        Promotion promotion = new Promotion();

        // Act
        promotionService.delete(promotion);

        // Verify interactions with repository and logger
        /*verify(newsRepository, times(1)).delete(news);
        verifyNoMoreInteractions(newsRepository);
        verify(log).info("Deleting News: {}", news);
        verify(log).info("Successfully deleted News: {}", news);
        verifyNoMoreInteractions(log);*/
    }

    @Test
    public void testDeleteNewsException() {
        // Arrange
        Promotion promotion = new Promotion();
        doThrow(new RuntimeException("Delete failed")).when(promotionRepository).delete(promotion);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> promotionService.delete(promotion));

        // Verify interactions with repository and logger
        /*verify(newsRepository, times(1)).delete(news);
        verifyNoMoreInteractions(newsRepository);
        verify(log).info("Deleting News: {}", news);
        verify(log).error("Error while deleting News: {}", news);
        verifyNoMoreInteractions(log);*/
    }
}
