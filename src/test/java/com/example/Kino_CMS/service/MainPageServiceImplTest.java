package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.AboutCinema;
import com.example.Kino_CMS.entity.KidsRoom;
import com.example.Kino_CMS.entity.MainPage;
import com.example.Kino_CMS.repository.MainBannersRepository;
import com.example.Kino_CMS.repository.MainPageRepository;
import com.example.Kino_CMS.service.impl.MainPageServiceImpl;
import org.apache.logging.log4j.Logger;
import org.jboss.jandex.Main;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class MainPageServiceImplTest {
    @Mock
    private MainPageRepository mainPageRepository;

    @InjectMocks
    private MainPageServiceImpl mainPageService;

    @Mock
    private Logger log;


    @BeforeEach
    public void setupLogger() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void setup() {
        // Отключите реальный логгер и используйте мокованный логгер
        MockitoAnnotations.openMocks(this);

        // Перехватываем вызовы методов логгера и выполняем проверки
        doAnswer(invocation -> {
            String message = (String) invocation.getArgument(0);
            System.out.println("Logger: " + message); // Для демонстрации
            return null;
        }).when(log).info(any(String.class), any(Object.class));
    }

    @Test
    public void testGetAllKidsRoomSuccess() {
        MainPage mockKidsRoom = new MainPage();
        List<MainPage> mockKidsRoomList = Collections.singletonList(mockKidsRoom);

        when(mainPageRepository.findAll()).thenReturn(mockKidsRoomList);

        Iterable<MainPage> result = mainPageService.getAllMainPages();

        assertNotNull(result);
        assertEquals(mockKidsRoomList, result);
    }


    @Test(expected = Exception.class)
    public void testGetAllMainPagesException() {
        // Подготовка тестовых данных (репозиторий выбросит исключение)
        when(mainPageRepository.findAll()).thenThrow(new RuntimeException("Test exception"));

        // Выполнение метода
        mainPageService.getAllMainPages();

        // Проверка вызовов логирования
        verify(log).info("Getting all MainPages");
        verify(log).error("Error while getting all MainPages", new RuntimeException("Test exception"));
    }


    @Test(expected = Exception.class)
    public void testGetMainPageByIdException() {
        Long mainPageId = 123L; // Замените на реальное значение ID

        // Подготовка тестовых данных (репозиторий выбросит исключение)
        when(mainPageRepository.findById(mainPageId)).thenThrow(new RuntimeException("Test exception"));

        // Выполнение метода
        mainPageService.getMainPageById(mainPageId);

        // Проверка вызовов логирования
        verify(log).info("Getting MainPage by ID: {}", mainPageId);
        verify(log).error("Error while getting MainPage by ID: {}", mainPageId, new RuntimeException("Test exception"));
    }

    @Test
    public void testSaveMainPage() {
        // Arrange
        MainPage mockKidsRoom = new MainPage();

        when(mainPageRepository.save(eq(mockKidsRoom))).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            mainPageService.saveMainPage(mockKidsRoom);
        });

        assertEquals("Test exception", exception.getMessage());

        // Verify that the save method was called
        verify(mainPageRepository, times(1)).save(mockKidsRoom);
    }

    @Test
    public void testSaveMainPageSuccess() {
        MainPage mockKidsRoom = new MainPage();

        when(mainPageRepository.save(any(MainPage.class))).thenReturn(mockKidsRoom);

        MainPage result = mainPageService.saveMainPage(mockKidsRoom);

        assertNotNull(result);
        assertEquals(mockKidsRoom, result);

        verify(mainPageRepository, times(1)).save(mockKidsRoom);
    }

    @Test
    public void testGetMainPageById_Success() {
        MockitoAnnotations.openMocks(this);

        Long mainPageId = 1L;
        MainPage mainPage = new MainPage();
        // Установите значения полей в mainPage

        when(mainPageRepository.findById(mainPageId)).thenReturn(Optional.of(mainPage));

        MainPage result = mainPageService.getMainPageById(mainPageId);

        assertEquals(mainPage, result);
    }
}
