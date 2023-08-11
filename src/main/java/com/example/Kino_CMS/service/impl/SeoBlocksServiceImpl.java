package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.SeoBlocks;
import com.example.Kino_CMS.repository.SeoBlocksRepository;
import com.example.Kino_CMS.service.SeoBlocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeoBlocksServiceImpl implements SeoBlocksService {
    @Autowired
    private SeoBlocksRepository seoBlocksRepository;


    @Override
    public SeoBlocks saveSeoBlock(SeoBlocks seoBlocks) {
        return seoBlocksRepository.save(seoBlocks);
    }
}
