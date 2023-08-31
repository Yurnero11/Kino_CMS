package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.Page;

import java.util.Optional;

public interface PageService {
    Page savePages(Page page);

    void delete(Page page);

    Optional<Page> findById(Long page_id);

    Iterable<Page> getAllPages();
}
