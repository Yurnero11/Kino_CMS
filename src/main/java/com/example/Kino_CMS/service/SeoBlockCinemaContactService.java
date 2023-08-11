package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.SeoBlockCinemaContact;

import java.util.Optional;

public interface SeoBlockCinemaContactService {
    Optional<SeoBlockCinemaContact> getSeoBlockById(Long seo_block_id);

    SeoBlockCinemaContact saveSeoBlock(SeoBlockCinemaContact seoBlockCinemaContact);
}
