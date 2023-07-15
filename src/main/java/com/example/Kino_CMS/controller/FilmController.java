package com.example.Kino_CMS.controller;

import com.example.Kino_CMS.entity.Gallary;
import com.example.Kino_CMS.entity.Movies;
import com.example.Kino_CMS.entity.SeoBlocks;
import com.example.Kino_CMS.entity.User;
import com.example.Kino_CMS.repository.GalleryRepository;
import com.example.Kino_CMS.repository.MovieRepository;
import com.example.Kino_CMS.repository.SeoBlocksRepository;
import com.example.Kino_CMS.service.MovieService;
import jakarta.servlet.ServletContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Controller
public class FilmController {
    private final MovieRepository movieRepository;
    private final SeoBlocksRepository seoBlocksRepository;
    private final GalleryRepository galleryRepository;

    private final MovieService movieService;


    public FilmController(MovieRepository movieRepository, SeoBlocksRepository seoBlocksRepository, GalleryRepository galleryRepository, MovieService movieService, ResourceLoader resourceLoader) {
        this.movieRepository = movieRepository;
        this.seoBlocksRepository = seoBlocksRepository;
        this.galleryRepository = galleryRepository;
        this.movieService = movieService;
    }

    @GetMapping("/films")
    public String film(Model model){
        Iterable<Movies> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "/films/page-films";
    }

    @GetMapping("/films/film-add")
    public String filmAdd(){
        return "/films/film-add";
    }

    @PostMapping("/films/film-add")
    public String filmAdd(
            @RequestParam("name_film") String name,
            @RequestParam("description") String description,
            @RequestParam("main_photo") MultipartFile upload,
            @RequestParam("gallery_photo_1") MultipartFile upload1,
            @RequestParam("gallery_photo_2") MultipartFile upload2,
            @RequestParam("gallery_photo_3") MultipartFile upload3,
            @RequestParam("gallery_photo_4") MultipartFile upload4,
            @RequestParam("gallery_photo_5") MultipartFile upload5,
            @RequestParam("link") String trailer_url,
            @RequestParam("movie_data") String movie_data,
            @RequestParam(value = "movie_type", required = false) String movie_type,
            @RequestParam("url") String url,
            @RequestParam("title") String title,
            @RequestParam("keywords") String keywords,
            @RequestParam("description_seo") String description_seo,
            ServletContext servletContext,
            RedirectAttributes redirectAttributes
    ) {
        Movies movies = new Movies();
        SeoBlocks seoBlocks = new SeoBlocks();
        Gallary gallary = new Gallary();

        movies.setName(name);
        movies.setTitle(description);
        movies.setMain_page_path(saveImage(upload));
        gallary.setImagePath1(saveImage(upload1));
        gallary.setImagePath2(saveImage(upload2));
        gallary.setImagePath3(saveImage(upload3));
        gallary.setImagePath4(saveImage(upload4));
        gallary.setImagePath5(saveImage(upload5));
        movies.setMovie_data(movie_data);
        movies.setTrailer_url(trailer_url);
        seoBlocks.setUrl(url);
        seoBlocks.setTitle(title);
        seoBlocks.setKeywords(keywords);
        seoBlocks.setDescription(description_seo);

        if (movie_type != null) {
            movies.setMovieType(movie_type);
        }

        movies.setGallery(gallary);
        movies.setSeoBlocks(seoBlocks);

        movieRepository.save(movies);
        galleryRepository.save(gallary);
        seoBlocksRepository.save(seoBlocks);

        return "redirect:/films";
    }

    @PostMapping("/films/{id}/remove")
    @PreAuthorize("hasRole('ADMIN')")
    public String removeFilm(@PathVariable(value = "id") long id, Model model){
        Movies movies = movieRepository.findById(id).orElseThrow();
        movieRepository.delete(movies);
        return "redirect:/films";
    }

    private final String uploadDir = "upload";

    private String saveImage(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
                String fileExtension = getFileExtension(originalFileName);
                String uniqueFileName = generateUniqueFileName(fileExtension);

                Path filePath = Paths.get(uploadDir, uniqueFileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                return uniqueFileName;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    private String generateUniqueFileName(String fileExtension) {
        String timeStamp = UUID.randomUUID().toString();
        return timeStamp + "." + fileExtension;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        } else {
            return "";
        }
    }
}
