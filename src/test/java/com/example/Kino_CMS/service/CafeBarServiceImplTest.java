package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.AboutMovie;
import com.example.Kino_CMS.entity.CafeBar;
import com.example.Kino_CMS.repository.AboutMovieRepository;
import com.example.Kino_CMS.repository.CafeBarRepository;
import com.example.Kino_CMS.service.impl.AboutMovieServiceImpl;
import com.example.Kino_CMS.service.impl.CafeBarServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CafeBarServiceImplTest {
    @Mock
    private CafeBarRepository cafeBarRepository;

    @InjectMocks
    private CafeBarServiceImpl cafeBarService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetExistingCafeBar() {
        int cafeId = 1;
        CafeBar existingCafeBar = new CafeBar();
        existingCafeBar.setCafe_id(cafeId);

        when(cafeBarRepository.findById((long) cafeId)).thenReturn(Optional.of(existingCafeBar));

        CafeBar result = cafeBarService.getCafeBarById((long) cafeId);

        assertNotNull(result);
        assertEquals(existingCafeBar.getCafe_id(), result.getCafe_id());

        verify(cafeBarRepository, times(1)).findById((long) cafeId);
    }

    @Test
    public void testGetNonExistingCafeBar() {
        Long cafeId = 2L;
        when(cafeBarRepository.findById(cafeId)).thenReturn(Optional.empty());

        CafeBar result = cafeBarService.getCafeBarById(cafeId);

        assertNotNull(result);
        assertEquals(0L, result.getCafe_id());

        verify(cafeBarRepository, times(1)).findById(cafeId);
    }

    @Test
    public void testGetCafeBarError() {
        Long cafeId = 3L;
        when(cafeBarRepository.findById(cafeId)).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cafeBarService.getCafeBarById(cafeId);
        });

        assertEquals("Test exception", exception.getMessage());

        verify(cafeBarRepository, times(1)).findById(cafeId);
    }

    @Test
    public void testGetAllCafeBars() {
        List<CafeBar> cafeBarList = new ArrayList<>();
        cafeBarList.add(new CafeBar());
        cafeBarList.add(new CafeBar());
        cafeBarList.add(new CafeBar());

        when(cafeBarRepository.findAll()).thenReturn(cafeBarList);

        Iterable<CafeBar> result = cafeBarService.getAllCafeBars();

        assertNotNull(result);
        assertEquals(cafeBarList.size(), ((List<CafeBar>) result).size());

        verify(cafeBarRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllCafeBarsEmpty() {
        when(cafeBarRepository.findAll()).thenReturn(new ArrayList<>());

        Iterable<CafeBar> result = cafeBarService.getAllCafeBars();

        assertNotNull(result);
        assertEquals(0, ((List<CafeBar>) result).size());

        verify(cafeBarRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllCafeBarsError() {
        when(cafeBarRepository.findAll()).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cafeBarService.getAllCafeBars();
        });

        assertEquals("Test exception", exception.getMessage());

        verify(cafeBarRepository, times(1)).findAll();
    }

    @Test
    public void testSaveCafeBarSuccess() {
        CafeBar mockCafeBar = new CafeBar();

        when(cafeBarRepository.save(any(CafeBar.class))).thenReturn(mockCafeBar);

        CafeBar result = cafeBarService.saveCafeBar(mockCafeBar);

        assertNotNull(result);
        assertEquals(mockCafeBar, result);

        verify(cafeBarRepository, times(1)).save(mockCafeBar);
    }

    @Test
    public void testSaveCafeBarError() {
        CafeBar mockCafeBar = new CafeBar();
        when(cafeBarRepository.save(eq(mockCafeBar))).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cafeBarService.saveCafeBar(mockCafeBar);
        });

        assertEquals("Test exception", exception.getMessage());

        // Verify that the save method was called
        verify(cafeBarRepository, times(1)).save(mockCafeBar);
    }
}
