package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.Page;
import com.example.Kino_CMS.repository.PageRepository;
import com.example.Kino_CMS.service.impl.PageServiceImpl;
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
public class PageServiceImplTest {
    @Mock
    private PageRepository pageRepository;

    @InjectMocks
    private PageServiceImpl pageService;

    @Mock
    public Logger log;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPagesSuccess() {
        Page mockCinema = new Page();
        List<Page> mockCinemaList = Collections.singletonList(mockCinema);

        when(pageRepository.findAll()).thenReturn(mockCinemaList);

        Iterable<Page> result = pageService.getAllPages();

        assertNotNull(result);
        assertEquals(mockCinemaList, result);
    }

    @Test
    public void testGetAllCinemasError() {
        when(pageRepository.findAll()).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pageService.getAllPages();
        });

        assertEquals("Test exception", exception.getMessage());
    }



    @Test
    public void testSaveCinemasSuccess() {
        Page mockCinema = new Page();

        when(pageRepository.save(any(Page.class))).thenReturn(mockCinema);

        Page result = pageService.savePages(mockCinema);

        assertNotNull(result);
        assertEquals(mockCinema, result);

        //verify(moviesService, times(1)).saveMovies(mockCinema);
    }

    @Test
    public void testSaveCinemasError() {
        Page mockCinema = new Page();

        when(pageRepository.save(eq(mockCinema))).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pageService.savePages(mockCinema);
        });

        assertEquals("Test exception", exception.getMessage());

        // Verify that the save method was called
        verify(pageRepository, times(1)).save(mockCinema);
    }

    @Test
    public void testGetCinemaByIdExistingCinema() {
        int cinemaId = 1;
        Page mockCinema = new Page();
        mockCinema.setPage_id(cinemaId);

        when(pageRepository.findById((long) cinemaId)).thenReturn(Optional.of(mockCinema));

        Optional<Page> result = pageService.findById((long) cinemaId);

        assertTrue(result.isPresent());
        assertEquals(mockCinema, result.get());

        verify(pageRepository, times(1)).findById((long) cinemaId);
    }

    @Test
    public void testGetCinemaByIdNonExistingCinema() {
        Long cinemaId = 1L;

        when(pageRepository.findById(cinemaId)).thenReturn(Optional.empty());

        Optional<Page> result = pageService.findById((long) cinemaId);

        assertFalse(result.isPresent());

        verify(pageRepository, times(1)).findById(cinemaId);
    }

    @Test(expected = Exception.class)
    public void testGetCinemaByIdException() {
        Long cinemaId = 123L; // замените на реальное значение ID

        // Подготовка тестовых данных (репозиторий выбросит исключение)
        when(pageRepository.findById(cinemaId)).thenThrow(new RuntimeException("Test exception"));

        // Выполнение метода
        pageService.findById(cinemaId);

        // Проверка вызовов логирования
        verify(log).info("Getting Movie by ID: {}", cinemaId);
        verify(log).error("Error while getting Movie by ID: {}", cinemaId, new RuntimeException("Test exception"));
    }


    @Test
    public void testDeleteNewsSuccess() {
        // Arrange
        Page mockCinema = new Page();

        // Act
        pageService.delete(mockCinema);

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
        Page mockCinema = new Page();
        doThrow(new RuntimeException("Delete failed")).when(pageRepository).delete(mockCinema);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> pageService.delete(mockCinema));

        // Verify interactions with repository and logger
        /*verify(newsRepository, times(1)).delete(news);
        verifyNoMoreInteractions(newsRepository);
        verify(log).info("Deleting News: {}", news);
        verify(log).error("Error while deleting News: {}", news);
        verifyNoMoreInteractions(log);*/
    }
}
