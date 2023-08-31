package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.News;
import com.example.Kino_CMS.repository.NewsRepository;
import com.example.Kino_CMS.service.impl.NewsServiceImpl;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class NewsServiceImplTest {
    @InjectMocks
    private NewsServiceImpl newsService;

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private Logger log;

    @Test
    public void testGetAllNewsSuccess() {
        News mockCinema = new News();
        List<News> mockCinemaList = Collections.singletonList(mockCinema);

        when(newsRepository.findAll()).thenReturn(mockCinemaList);

        Iterable<News> result = newsService.getAllNews();

        assertNotNull(result);
        assertEquals(mockCinemaList, result);
    }

    @Test
    public void testGetAllCinemasError() {
        when(newsRepository.findAll()).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            newsService.getAllNews();
        });

        assertEquals("Test exception", exception.getMessage());
    }



    @Test
    public void testSaveCinemasSuccess() {
        News mockCinema = new News();

        when(newsRepository.save(any(News.class))).thenReturn(mockCinema);

        News result = newsService.saveNews(mockCinema);

        assertNotNull(result);
        assertEquals(mockCinema, result);

        //verify(moviesService, times(1)).saveMovies(mockCinema);
    }

    @Test
    public void testSaveCinemasError() {
        News mockCinema = new News();

        when(newsRepository.save(eq(mockCinema))).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            newsService.saveNews(mockCinema);
        });

        assertEquals("Test exception", exception.getMessage());

        // Verify that the save method was called
        verify(newsRepository, times(1)).save(mockCinema);
    }

    @Test
    public void testGetCinemaByIdExistingCinema() {
        int cinemaId = 1;
        News mockCinema = new News();
        mockCinema.setNews_id(cinemaId);

        when(newsRepository.findById((long) cinemaId)).thenReturn(Optional.of(mockCinema));

        Optional<News> result = newsService.getNewsById((long) cinemaId);

        assertTrue(result.isPresent());
        assertEquals(mockCinema, result.get());

        verify(newsRepository, times(1)).findById((long) cinemaId);
    }

    @Test
    public void testGetCinemaByIdNonExistingCinema() {
        Long cinemaId = 1L;

        when(newsRepository.findById(cinemaId)).thenReturn(Optional.empty());

        Optional<News> result = newsService.getNewsById((long) cinemaId);

        assertFalse(result.isPresent());

        verify(newsRepository, times(1)).findById(cinemaId);
    }

    @Test(expected = Exception.class)
    public void testGetCinemaByIdException() {
        Long cinemaId = 123L; // замените на реальное значение ID

        // Подготовка тестовых данных (репозиторий выбросит исключение)
        when(newsRepository.findById(cinemaId)).thenThrow(new RuntimeException("Test exception"));

        // Выполнение метода
        newsService.getNewsById(cinemaId);

        // Проверка вызовов логирования
        verify(log).info("Getting Movie by ID: {}", cinemaId);
        verify(log).error("Error while getting Movie by ID: {}", cinemaId, new RuntimeException("Test exception"));
    }


    @Test
    public void testDeleteNewsSuccess() {
        // Arrange
        News news = new News(/* initialize the News object */);

        // Act
        newsService.delete(news);

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
        News news = new News(/* initialize the News object */);
        doThrow(new RuntimeException("Delete failed")).when(newsRepository).delete(news);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> newsService.delete(news));

        // Verify interactions with repository and logger
        /*verify(newsRepository, times(1)).delete(news);
        verifyNoMoreInteractions(newsRepository);
        verify(log).info("Deleting News: {}", news);
        verify(log).error("Error while deleting News: {}", news);
        verifyNoMoreInteractions(log);*/
    }


}
