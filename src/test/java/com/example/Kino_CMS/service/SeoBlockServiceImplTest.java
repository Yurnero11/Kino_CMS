package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.SeoBlock;
import com.example.Kino_CMS.repository.SeoBlocksRepository;
import com.example.Kino_CMS.service.impl.SeoBlocksServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class SeoBlockServiceImplTest {
    @Mock
    private SeoBlocksRepository seoBlocksRepository;

    @InjectMocks
    private SeoBlocksServiceImpl seoBlocksService; // Use the concrete implementation class

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveSeoBlock() {
        SeoBlock seoBlockToSave = new SeoBlock();
        SeoBlock savedSeoBlock = new SeoBlock();

        when(seoBlocksRepository.save(seoBlockToSave)).thenReturn(savedSeoBlock);

        SeoBlock result = seoBlocksService.saveSeoBlock(seoBlockToSave);

        // Verify that the repository's save method was called with the correct argument
        verify(seoBlocksRepository, times(1)).save(seoBlockToSave);

        // Verify that the returned result matches the expected savedSeoBlocks
        assertEquals(savedSeoBlock, result);
    }
}
