package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.AboutCinema;
import com.example.Kino_CMS.repository.AboutCinemaRepository;
import com.example.Kino_CMS.service.impl.AboutCinemaServiceImpl;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class AboutCinemaServiceImplTest {
    @Mock
    private AboutCinemaRepository aboutCinemaRepository;

    @InjectMocks
    private AboutCinemaServiceImpl aboutCinemaService;

    @Mock
    private Logger log;

    @Before
    public void setUp() {
        AboutCinema mockMovie = new AboutCinema();
    }

    @Test
    public void testGetAboutCinemaByIdSuccess() {
        Long cinemaId = 1L;
        AboutCinema mockCinema = new AboutCinema();

        when(aboutCinemaRepository.findById(cinemaId)).thenReturn(Optional.of(mockCinema));

        AboutCinema result = aboutCinemaService.getAboutCinemaById(cinemaId);

        assertNotNull(result);
        verify(aboutCinemaRepository, times(1)).findById(cinemaId);
    }

    @Test
    public void testGetAboutCinemaByIdNotFound() {
        Long cinemaId = 2L;

        AboutCinema result = aboutCinemaService.getAboutCinemaById(cinemaId);

        //assertNull(result);
        verify(aboutCinemaRepository, times(1)).findById(cinemaId);
    }

    @Test(expected = RuntimeException.class)
    public void testGetAboutCinemaByIdError() {
        Long cinemaId = 999L;
        when(aboutCinemaRepository.findById(eq(cinemaId))).thenThrow(new RuntimeException("Test exception"));

        aboutCinemaService.getAboutCinemaById(cinemaId);
    }


    @Test
    public void testSaveAboutCinema() {
        AboutCinema mockCinema = new AboutCinema();

        when(aboutCinemaRepository.save(any(AboutCinema.class))).thenReturn(mockCinema); // Мокирование сохранения

        AboutCinema result = aboutCinemaService.saveAboutCinema(mockCinema);

        assertNotNull(result);
        verify(aboutCinemaRepository, times(1)).save(mockCinema);
    }

    @Test
    public void testGetAllAboutCinemaSuccess() {
        List<AboutCinema> mockCinemas = new ArrayList<>();
        mockCinemas.add(new AboutCinema());
        mockCinemas.add(new AboutCinema());

        when(aboutCinemaRepository.findAll()).thenReturn(mockCinemas);

        Iterable<AboutCinema> result = aboutCinemaService.getAllAboutCinema();

        assertNotNull(result);
        assertTrue(result.iterator().hasNext());
        verify(aboutCinemaRepository, times(1)).findAll();
    }

    @Test(expected = RuntimeException.class)
    public void testGetAllAboutCinemaError() {
        when(aboutCinemaRepository.findAll()).thenThrow(new RuntimeException("Test exception"));

        aboutCinemaService.getAllAboutCinema();
    }

    @Test
    public void testGetAllAboutCinemaEmptyList() {
        when(aboutCinemaRepository.findAll()).thenReturn(new ArrayList<>());

        Iterable<AboutCinema> result = aboutCinemaService.getAllAboutCinema();

        assertNotNull(result);
        assertFalse(result.iterator().hasNext());
        verify(aboutCinemaRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllAboutCinemaNotFound() {
        when(aboutCinemaRepository.findAll()).thenReturn(Collections.emptyList());

        Iterable<AboutCinema> result = aboutCinemaService.getAllAboutCinema();

        assertNotNull(result);
        assertFalse(result.iterator().hasNext());

        verify(aboutCinemaRepository, times(1)).findAll();
    }

    @Test(expected = Exception.class)
    public void testSaveAboutCinemaException() {
        AboutCinema aboutCinema = new AboutCinema(/* конструктор AboutCinema */);

        // Подготовка тестовых данных (репозиторий выбросит исключение)
        when(aboutCinemaRepository.save(any(AboutCinema.class))).thenThrow(new RuntimeException("Test exception"));

        // Выполнение метода
        aboutCinemaService.saveAboutCinema(aboutCinema);

        // Проверка вызовов логирования
        verify(log).info("Saving AboutCinema: {}", aboutCinema);
        verify(log).error("Error while saving AboutCinema: {}", aboutCinema, new RuntimeException("Test exception"));
    }



}