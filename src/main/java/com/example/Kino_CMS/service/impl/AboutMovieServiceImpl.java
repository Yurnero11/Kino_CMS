package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.AboutMovie;
import com.example.Kino_CMS.repository.AboutMovieRepository;
import com.example.Kino_CMS.service.AboutMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AboutMovieServiceImpl implements AboutMovieService {
    @Autowired
    private AboutMovieRepository aboutMovieRepository;

    @Override
    public AboutMovie saveAboutMovie(AboutMovie aboutMovie) {
        return aboutMovieRepository.save(aboutMovie);
    }
}
