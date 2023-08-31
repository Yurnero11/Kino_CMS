package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.AboutMovie;
import com.example.Kino_CMS.repository.AboutMovieRepository;
import com.example.Kino_CMS.service.impl.AboutMovieServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class AboutMovieServiceImplTest {
    @InjectMocks
    private AboutMovieServiceImpl aboutMovieService;

    @Mock
    private AboutMovieRepository aboutMovieRepository;

    @Before
    public void setUp() {
        AboutMovie mockMovie = new AboutMovie();
    }

    @Test
    public void testSaveAboutMovieSuccess() {
        AboutMovie mockAboutMovie = new AboutMovie(); // Создаем мок объект

        when(aboutMovieRepository.save(any(AboutMovie.class))).thenReturn(mockAboutMovie); // Мокирование сохранения

        AboutMovie result = aboutMovieService.saveAboutMovie(mockAboutMovie);

        assertNotNull(result);
        verify(aboutMovieRepository, times(1)).save(mockAboutMovie);
    }

    @Test
    public void testSaveAboutMovieError() {
        AboutMovie mockAboutMovie = new AboutMovie();

        when(aboutMovieRepository.save(any(AboutMovie.class))).thenThrow(new RuntimeException("Test exception"));

        assertThrows(RuntimeException.class, () -> aboutMovieService.saveAboutMovie(mockAboutMovie));
    }


}