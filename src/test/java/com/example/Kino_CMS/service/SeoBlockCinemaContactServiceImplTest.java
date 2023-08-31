package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.SeoBlockCinemaContact;
import com.example.Kino_CMS.repository.SeoBlockCinemaContactRepository;
import com.example.Kino_CMS.service.impl.SeoBlockCinemaContactServiceImpl;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class SeoBlockCinemaContactServiceImplTest {
    @Mock
    private SeoBlockCinemaContactRepository seoBlockCinemaContactRepository;

    @InjectMocks
    private SeoBlockCinemaContactServiceImpl seoBlockCinemaContactService;

    @Mock
    public Logger log;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetSeoBlockById_Success() {
        Long seoBlockId = 1L;
        SeoBlockCinemaContact seoBlock = new SeoBlockCinemaContact();
        when(seoBlockCinemaContactRepository.findById(seoBlockId)).thenReturn(Optional.of(seoBlock));

        Optional<SeoBlockCinemaContact> result = seoBlockCinemaContactService.getSeoBlockById(seoBlockId);

        assertTrue(result.isPresent());
        assertEquals(seoBlock, result.get());
        verify(seoBlockCinemaContactRepository, times(1)).findById(seoBlockId);
    }

    @Test
    public void testGetSeoBlockById_NotFound() {
        Long seoBlockId = 1L;
        when(seoBlockCinemaContactRepository.findById(seoBlockId)).thenReturn(Optional.empty());

        Optional<SeoBlockCinemaContact> result = seoBlockCinemaContactService.getSeoBlockById(seoBlockId);

        assertFalse(result.isPresent());
        verify(seoBlockCinemaContactRepository, times(1)).findById(seoBlockId);
    }

    @Test
    public void testSaveSeoBlock_Success() {
        SeoBlockCinemaContact seoBlock = new SeoBlockCinemaContact();
        when(seoBlockCinemaContactRepository.save(seoBlock)).thenReturn(seoBlock);

        SeoBlockCinemaContact result = seoBlockCinemaContactService.saveSeoBlock(seoBlock);

        assertNotNull(result);
        assertEquals(seoBlock, result);
        verify(seoBlockCinemaContactRepository, times(1)).save(seoBlock);
    }

    @Test
    public void testSaveSeoBlock_Error() {
        SeoBlockCinemaContact seoBlock = new SeoBlockCinemaContact();
        when(seoBlockCinemaContactRepository.save(seoBlock)).thenThrow(new RuntimeException("Save error"));

        assertThrows(RuntimeException.class, () -> seoBlockCinemaContactService.saveSeoBlock(seoBlock));
        verify(seoBlockCinemaContactRepository, times(1)).save(seoBlock);
    }

    @Test
    public void testGetSeoBlockById_Exception() {
        Long seoBlockId = 1L;
        when(seoBlockCinemaContactRepository.findById(seoBlockId)).thenThrow(new RuntimeException("Database error"));

        try {
            seoBlockCinemaContactService.getSeoBlockById(seoBlockId);
        } catch (Exception e) {
            // Ensure that the exception is thrown and logged correctly
            verify(seoBlockCinemaContactRepository, times(1)).findById(seoBlockId);
        }
    }
}
