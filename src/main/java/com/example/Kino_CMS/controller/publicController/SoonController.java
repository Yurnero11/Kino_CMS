package com.example.Kino_CMS.controller.publicController;

import com.example.Kino_CMS.entity.Gallary;
import com.example.Kino_CMS.entity.Movies;
import com.example.Kino_CMS.repository.MovieRepository;
import com.example.Kino_CMS.service.impl.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class SoonController {
    private final MovieServiceImpl movieServiceImpl;
    private final MovieRepository movieRepository;

    @Autowired
    public SoonController(MovieServiceImpl movieServiceImpl, MovieRepository movieRepository) {
        this.movieServiceImpl = movieServiceImpl;
        this.movieRepository = movieRepository;
    }

    @GetMapping("/soon")
    public String soonPage(Model model){
        Iterable<Movies> movies = movieServiceImpl.getAllMovies();
        model.addAttribute("movies", movies);
        return "/public/soon/soon-page";
    }

    @GetMapping("/soon/{movie_id}")
    public String soonPage(@PathVariable(value = "movie_id") long id, Model model){
        Optional<Movies> optionalMovies = movieRepository.findById(id);
        if (optionalMovies.isEmpty()) {
            return "redirect:/posts";
        }
        Movies movies = optionalMovies.get();

        Gallary gallery = movieServiceImpl.getGalleryByMovieId((long) movies.getMovie_id());
        if (gallery != null) {
            List<String> galleryPaths = new ArrayList<>();
            galleryPaths.add(gallery.getImagePath1());
            galleryPaths.add(gallery.getImagePath2());
            galleryPaths.add(gallery.getImagePath3());
            galleryPaths.add(gallery.getImagePath4());
            galleryPaths.add(gallery.getImagePath5());
            model.addAttribute("galleryPaths", galleryPaths);
        }


        // Добавление объекта cinema в модель
        model.addAttribute("movies", movies);
        return "/public/posts/film-page";
    }
}
