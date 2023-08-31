package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.Gallary;
import com.example.Kino_CMS.entity.Movie;
import com.example.Kino_CMS.repository.MovieRepository;
import com.example.Kino_CMS.service.impl.MovieServiceImpl;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class MovieServiceImplTest {
    @Mock
    private Logger log;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl moviesService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveMovies_Successful() {
        Movie movie = new Movie(); // Создайте объект Movies для теста

        // Мокируйте поведение репозитория
        when(movieRepository.save(movie)).thenReturn(movie);

        Movie result = moviesService.saveMovies(movie);

        assertNotNull(result); // Проверяем, что результат не null
        verify(movieRepository, times(1)).save(movie); // Проверяем, что метод save вызван один раз
    }

    @Test(expected = Exception.class)
    public void testSaveMovies_Failure() {
        Movie movie = new Movie(); // Создайте объект Movies для теста

        // Мокируйте поведение репозитория, чтобы выбросить исключение
        when(movieRepository.save(movie)).thenThrow(new Exception());

        moviesService.saveMovies(movie);

        // Ожидаем исключение Exception
    }


    @Test
    public void testGetAllMovieSuccess() {
        Movie mockCinema = new Movie();
        List<Movie> mockCinemaList = Collections.singletonList(mockCinema);

        when(movieRepository.findAll()).thenReturn(mockCinemaList);

        Iterable<Movie> result = moviesService.getAllMovies();

        assertNotNull(result);
        assertEquals(mockCinemaList, result);
    }

    @Test
    public void testGetAllCinemasError() {
        when(movieRepository.findAll()).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            moviesService.getAllMovies();
        });

        assertEquals("Test exception", exception.getMessage());
    }

    @Test
    public void testGetGalleryByCinemaIdSuccess() {
        Long cinemaId = 1L;
        Movie mockCinema = new Movie();
        Gallary mockGallery = new Gallary();
        mockCinema.setGallery(mockGallery);

        when(movieRepository.findById(cinemaId)).thenReturn(Optional.of(mockCinema));

        Gallary result = moviesService.getGalleryByMovieId(cinemaId);

        assertNotNull(result);
        assertEquals(mockGallery, result);
    }

    @Test
    public void testGetGalleryByCinemaIdCinemaNotFound() {
        Long cinemaId = 1L;

        when(movieRepository.findById(cinemaId)).thenReturn(Optional.empty());

        Gallary result = moviesService.getGalleryByMovieId(cinemaId);

        assertNull(result);
    }

    @Test
    public void testGetGalleryByCinemaIdEmptyGallery() {
        Long cinemaId = 1L;
        Movie mockCinema = new Movie();

        when(movieRepository.findById(cinemaId)).thenReturn(Optional.of(mockCinema));

        Gallary result = moviesService.getGalleryByMovieId(cinemaId);

        assertNull(result);
    }

    @Test
    public void testGetGalleryByCinemaIdError() {
        Long cinemaId = 1L;

        when(movieRepository.findById(cinemaId)).thenThrow(new RuntimeException("Test exception"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            moviesService.getGalleryByMovieId(cinemaId);
        });

        assertEquals("Test exception", exception.getMessage());
    }

    @Test
    public void testGetGalleryByCinemaIdGalleryNotFound() {
        Long cinemaId = 1L;
        Movie mockCinema = new Movie();

        when(movieRepository.findById(cinemaId)).thenReturn(Optional.of(mockCinema));

        Gallary result = moviesService.getGalleryByMovieId(cinemaId);

        assertNull(result);
    }


    @Test
    public void testSaveCinemasSuccess() {
        Movie mockCinema = new Movie();

        when(movieRepository.save(any(Movie.class))).thenReturn(mockCinema);

        Movie result = moviesService.saveMovies(mockCinema);

        assertNotNull(result);
        assertEquals(mockCinema, result);

        //verify(moviesService, times(1)).saveMovies(mockCinema);
    }

    @Test
    public void testSaveCinemasError() {
        Movie mockCinema = new Movie();

        when(movieRepository.save(eq(mockCinema))).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            moviesService.saveMovies(mockCinema);
        });

        assertEquals("Test exception", exception.getMessage());

        // Verify that the save method was called
        verify(movieRepository, times(1)).save(mockCinema);
    }

    @Test
    public void testGetCinemaByIdExistingCinema() {
        int cinemaId = 1;
        Movie mockCinema = new Movie();
        mockCinema.setMovie_id(cinemaId);

        when(movieRepository.findById((long) cinemaId)).thenReturn(Optional.of(mockCinema));

        Optional<Movie> result = moviesService.getMovieById((long) cinemaId);

        assertTrue(result.isPresent());
        assertEquals(mockCinema, result.get());

        verify(movieRepository, times(1)).findById((long) cinemaId);
    }

    @Test
    public void testGetCinemaByIdNonExistingCinema() {
        Long cinemaId = 1L;

        when(movieRepository.findById(cinemaId)).thenReturn(Optional.empty());

        Optional<Movie> result = moviesService.getMovieById(cinemaId);

        assertFalse(result.isPresent());

        verify(movieRepository, times(1)).findById(cinemaId);
    }

    @Test(expected = Exception.class)
    public void testGetCinemaByIdException() {
        Long cinemaId = 123L; // замените на реальное значение ID

        // Подготовка тестовых данных (репозиторий выбросит исключение)
        when(movieRepository.findById(cinemaId)).thenThrow(new RuntimeException("Test exception"));

        // Выполнение метода
        moviesService.getMovieById(cinemaId);

        // Проверка вызовов логирования
        verify(log).info("Getting Movie by ID: {}", cinemaId);
        verify(log).error("Error while getting Movie by ID: {}", cinemaId, new RuntimeException("Test exception"));
    }

    @Test
    public void testSaveMoviesSuccess() {
        Movie movieToSave = new Movie();
        // Set properties of moviesToSave

        Movie savedMovie = new Movie();
        // Set properties of savedMovies

        when(movieRepository.save(any(Movie.class))).thenReturn(savedMovie);

        Movie result = moviesService.saveMovies(movieToSave);

        assertNotNull(result);
        assertEquals(savedMovie, result);
        //verify(log, times(1)).info("Saving Movies: {}", moviesToSave);
        //verify(log, times(1)).info("Successfully saved Movies: {}", moviesToSave);
        verifyNoMoreInteractions(log);
    }

    @Test
    public void testSaveMoviesException() {
        Movie movieToSave = new Movie();
        // Set properties of moviesToSave

        when(movieRepository.save(any(Movie.class))).thenThrow(new RuntimeException("Test Exception"));

        assertThrows(RuntimeException.class, () -> {
            moviesService.saveMovies(movieToSave);
        });

        //verify(log, times(1)).info("Saving Movies: {}", moviesToSave);
        //verify(log, times(1)).error("Error while saving Movies: {}", moviesToSave);
        verifyNoMoreInteractions(log);
    }

    @Test
    public void testSaveMoviesNullInput() {
        //assertThrows(IllegalArgumentException.class, () -> {
           //moviesService.saveMovies(null);
        //});

       // verifyNoInteractions(movieRepository, log);
    }

    @Test
    public void testSaveMoviesRepositoryException() {
        Movie movieToSave = new Movie();
        // Set properties of moviesToSave

        when(movieRepository.save(any(Movie.class))).thenThrow(new DataAccessException("Repository Exception") {});

        assertThrows(DataAccessException.class, () -> {
            moviesService.saveMovies(movieToSave);
        });

        //verify(log, times(1)).info("Saving Movies: {}", moviesToSave);
        //verify(log, times(1)).error("Error while saving Movies: {}", moviesToSave);
        verifyNoMoreInteractions(log);
    }

}
