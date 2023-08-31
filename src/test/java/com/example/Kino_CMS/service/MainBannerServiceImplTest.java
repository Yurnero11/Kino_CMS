package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.MainBanner;
import com.example.Kino_CMS.repository.MainBannersRepository;
import com.example.Kino_CMS.service.impl.MainBannerServiceImpl;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class MainBannerServiceImplTest {
    @Mock
    private MainBannersRepository mainBannersRepository;

    @Mock
    private Logger log;

    @InjectMocks
    private MainBannerServiceImpl mainBannerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetLatestMainBannerSuccess() {
        // Подготовка тестовых данных
        MainBanner testBanner = new MainBanner(/* конструктор MainBanners */);
        when(mainBannersRepository.findFirstByOrderByBannerIdDesc()).thenReturn(testBanner);

        // Выполнение метода
        MainBanner result = mainBannerService.getLatestMainBanner();

        // Проверка результатов
        assertNotNull(result);
        assertEquals(testBanner, result);
    }

    @Test
    public void testGetLatestMainBannerNoBanner() {
        // Подготовка тестовых данных (репозиторий вернет null)
        when(mainBannersRepository.findFirstByOrderByBannerIdDesc()).thenReturn(null);

        // Выполнение метода
        MainBanner result = mainBannerService.getLatestMainBanner();

        // Проверка результатов
        assertNull(result);
    }


    @Test
    public void testGetMainBannersExisting() {
        // Подготовка тестовых данных
        MainBanner testBanner = new MainBanner(/* конструктор MainBanners */);
        when(mainBannersRepository.findById(27)).thenReturn(Optional.of(testBanner));

        // Выполнение метода
        MainBanner result = mainBannerService.getMainBanners();

        // Проверка результатов
        assertNotNull(result);
        assertEquals(testBanner, result);
    }

    @Test
    public void testGetMainBannersNew() {
        // Подготовка тестовых данных (репозиторий вернет пустой Optional)
        when(mainBannersRepository.findById(27)).thenReturn(Optional.empty());

        // Выполнение метода
        MainBanner result = mainBannerService.getMainBanners();

        // Проверка результатов
        assertNotNull(result);
        // Здесь можно добавить дополнительные проверки для нового объекта MainBanners
    }

    @Test(expected = Exception.class)
    public void testGetMainBannersException() {
        // Подготовка тестовых данных (репозиторий выбросит исключение)
        when(mainBannersRepository.findById(27)).thenThrow(new Exception("Test exception"));

        // Выполнение метода
        mainBannerService.getMainBanners();

        // Ожидается выброс исключения
    }


    @Test
    public void testGetAllMainBannersSuccess() {
        // Подготовка тестовых данных
        List<MainBanner> testBanners = new ArrayList<>();
        testBanners.add(new MainBanner(/* конструктор MainBanners */));
        testBanners.add(new MainBanner(/* конструктор MainBanners */));
        when(mainBannersRepository.findAll()).thenReturn(testBanners);

        // Выполнение метода
        Iterable<MainBanner> result = mainBannerService.getAllMainBanners();

        // Проверка результатов
        assertNotNull(result);
        assertEquals(testBanners, result);
    }

    @Test
    public void testGetAllMainBannersEmpty() {
        // Подготовка тестовых данных (репозиторий вернет пустой список)
        List<MainBanner> testBanners = Collections.emptyList();
        when(mainBannersRepository.findAll()).thenReturn(testBanners);

        // Выполнение метода
        Iterable<MainBanner> result = mainBannerService.getAllMainBanners();

        // Проверка результатов
        assertNotNull(result);
        assertTrue(StreamSupport.stream(result.spliterator(), false).count() == 0);
    }

    @Test(expected = Exception.class)
    public void testGetAllMainBannersException() {
        // Подготовка тестовых данных (репозиторий выбросит исключение)
        when(mainBannersRepository.findAll()).thenThrow(new Exception("Test exception"));

        // Выполнение метода
        mainBannerService.getAllMainBanners();

        // Ожидается выброс исключения
    }


    @Test
    public void testSaveMainBannersSuccess() {
        // Подготовка тестовых данных
        MainBanner testBanner = new MainBanner(/* конструктор MainBanners */);
        when(mainBannersRepository.save(any(MainBanner.class))).thenReturn(testBanner);

        // Выполнение метода
        MainBanner result = mainBannerService.saveMainBanners(testBanner);

        // Проверка результатов
        assertNotNull(result);
        assertEquals(testBanner, result);
    }

    @Test(expected = Exception.class)
    public void testSaveMainBannersException() {
        // Подготовка тестовых данных (репозиторий выбросит исключение)
        when(mainBannersRepository.save(any(MainBanner.class))).thenThrow(new Exception("Test exception"));

        // Выполнение метода
        mainBannerService.saveMainBanners(new MainBanner(/* конструктор MainBanners */));

        // Ожидается выброс исключения
    }


    @Test(expected = Exception.class)
    public void testGetLatestMainBannerException() {
        // Подготовка тестовых данных (репозиторий выбросит исключение)
        when(mainBannersRepository.findFirstByOrderByBannerIdDesc()).thenThrow(new RuntimeException("Test exception"));

        // Выполнение метода
        mainBannerService.getLatestMainBanner();

        // Проверка вызовов логирования
        verify(log).info("Getting latest MainBanner");
        verify(log).error("Error while getting latest MainBanner", new RuntimeException("Test exception"));
    }

    @Test(expected = Exception.class)
    public void testGetMainBannersException1() {
        // Подготовка тестовых данных (репозиторий выбросит исключение)
        when(mainBannersRepository.findById(27)).thenThrow(new RuntimeException("Test exception"));

        // Выполнение метода
        mainBannerService.getMainBanners();

        // Проверка вызовов логирования
        verify(log).info("MainBanners not found in the database, creating a new one.");
        verify(log).error("Error while retrieving MainBanners: Test exception");
    }

    @Test(expected = Exception.class)
    public void testGetAllMainBannersException2() {
        // Подготовка тестовых данных (репозиторий выбросит исключение)
        when(mainBannersRepository.findAll()).thenThrow(new RuntimeException("Test exception"));

        // Выполнение метода
        mainBannerService.getAllMainBanners();

        // Проверка вызовов логирования
        verify(log).info("Getting all MainBanners");
        verify(log).error("Error while getting all MainBanners", new RuntimeException("Test exception"));
    }

    @Test(expected = Exception.class)
    public void testSaveMainBannersException3() {
        // Подготовка тестовых данных (репозиторий выбросит исключение)
        when(mainBannersRepository.save(any(MainBanner.class))).thenThrow(new RuntimeException("Test exception"));

        // Выполнение метода
        mainBannerService.saveMainBanners(new MainBanner(/* конструктор MainBanners */));

        // Проверка вызовов логирования
        verify(log).info(eq("Saving MainBanners: {}"), any(MainBanner.class));
        verify(log).error(eq("Error while saving MainBanners: {}"), any(MainBanner.class), eq(new RuntimeException("Test exception")));
    }

}
