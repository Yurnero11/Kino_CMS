package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.Pages;

import java.util.Optional;

public interface PageService {
    Pages savePages(Pages pages);

    void delete(Pages pages);

    Optional<Pages> findById(Long page_id);

    Iterable<Pages> getAllPages();
}
