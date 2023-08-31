package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.Advertising;
import com.example.Kino_CMS.repository.AdvertisingRepository;
import com.example.Kino_CMS.service.impl.AdvertisingServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AdvertisingServiceImplTest {
    @InjectMocks
    private AdvertisingServiceImpl advertisingService;

    @Mock
    private AdvertisingRepository advertisingRepository;

    @Before
    public void setUp() {
        Advertising mockAdvertising = new Advertising();
    }

    @Test
    public void testGetAllAdvertisingSuccess() {
        List<Advertising> mockAdvertisingList = new ArrayList<>(); // Создаем мок список рекламы
        mockAdvertisingList.add(new Advertising());
        mockAdvertisingList.add(new Advertising());

        when(advertisingRepository.findAll()).thenReturn(mockAdvertisingList); // Мокирование получения списка рекламы

        Iterable<Advertising> result = advertisingService.getAllAdvertising();

        assertNotNull(result);

        // Используем Stream API для подсчета элементов
        long count = StreamSupport.stream(result.spliterator(), false).count();
        assertTrue(count > 0);

        verify(advertisingRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllAdvertisingError() {
        when(advertisingRepository.findAll()).thenThrow(new RuntimeException("Test exception"));

        assertThrows(RuntimeException.class, () -> advertisingService.getAllAdvertising());
    }

    @Test
    public void testGetAllAdvertisingEmptyList() {
        when(advertisingRepository.findAll()).thenReturn(new ArrayList<>());

        Iterable<Advertising> result = advertisingService.getAllAdvertising();

        assertNotNull(result);
        assertFalse(result.iterator().hasNext());
        verify(advertisingRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllAdvertisingEmptyListReal() {
        Iterable<Advertising> result = advertisingService.getAllAdvertising();

        assertNotNull(result);
        assertFalse(result.iterator().hasNext());
    }

    @Test
    public void testGetAllAdvertisingIntegration() {
        Advertising ad1 = new Advertising();
        ad1.setAdvertising_name("Ad 1");
        Advertising ad2 = new Advertising();
        ad2.setAdvertising_name("Ad 2");

        advertisingRepository.save(ad1);
        advertisingRepository.save(ad2);

        // Добавим ожидание
        try {
            Thread.sleep(1000); // Подождем 1 секунду
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Iterable<Advertising> result = advertisingService.getAllAdvertising();

        assertNotNull(result);

        // Используем Stream API для подсчета элементов
        long count = StreamSupport.stream(result.spliterator(), false).count();
        assertEquals(0, count);
    }

    @Test
    public void testGetAdvertisingByIdSuccess() {
        Long advertisingId = 1L;
        Advertising mockAdvertising = new Advertising();

        when(advertisingRepository.findById(advertisingId)).thenReturn(Optional.of(mockAdvertising));

        Optional<Advertising> result = advertisingService.getAdvertisingById(advertisingId);

        assertTrue(result.isPresent());
        verify(advertisingRepository, times(1)).findById(advertisingId);
    }

    @Test
    public void testGetAdvertisingByIdNotFound() {
        Long advertisingId = 2L;

        Optional<Advertising> result = advertisingService.getAdvertisingById(advertisingId);

        assertFalse(result.isPresent());
        verify(advertisingRepository, times(1)).findById(advertisingId);
    }

    @Test(expected = RuntimeException.class)
    public void testGetAdvertisingByIdError() {
        Long advertisingId = 999L;
        when(advertisingRepository.findById(eq(advertisingId))).thenThrow(new RuntimeException("Test exception"));

        advertisingService.getAdvertisingById(advertisingId);
    }


}
