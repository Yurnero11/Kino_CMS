package com.example.Kino_CMS.controller.adminController;

import com.example.Kino_CMS.entity.AboutMovie;
import com.example.Kino_CMS.entity.Gallary;
import com.example.Kino_CMS.entity.Movies;
import com.example.Kino_CMS.entity.SeoBlocks;
import com.example.Kino_CMS.repository.AboutMovieRepository;
import com.example.Kino_CMS.repository.GalleryRepository;
import com.example.Kino_CMS.repository.MovieRepository;
import com.example.Kino_CMS.repository.SeoBlocksRepository;
import com.example.Kino_CMS.service.impl.MovieServiceImpl;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.UUID;

import java.sql.Time;
import java.time.LocalTime;

@Controller
public class FilmController {
    @Autowired
    private  MovieRepository movieRepository;
    @Autowired
    private  SeoBlocksRepository seoBlocksRepository;
    @Autowired
    private  GalleryRepository galleryRepository;
    @Autowired
    private AboutMovieRepository aboutMovieRepository;
    @Autowired
    private MovieServiceImpl movieServiceImpl;


    @GetMapping("/admin/films")
    public String film(Model model){
        Iterable<Movies> movies = movieServiceImpl.getAllMovies();
        model.addAttribute("movies", movies);
        return "/admin/films/page-films";
    }

    @GetMapping("/admin/films/film-add")
    public String filmAdd(){
        return "/admin/films/film-add";
    }

    @PostMapping("/admin/films/film-add")
    public String filmAdd(
            @RequestParam("name_film") String name,
            @RequestParam("description") String description,
            @RequestParam("main_photo") MultipartFile upload,
            @RequestParam("gallery_photo_1") MultipartFile upload1,
            @RequestParam("gallery_photo_2") MultipartFile upload2,
            @RequestParam("gallery_photo_3") MultipartFile upload3,
            @RequestParam("gallery_photo_4") MultipartFile upload4,
            @RequestParam("gallery_photo_5") MultipartFile upload5,

            @RequestParam("year") Integer year,
            @RequestParam("composer") String composer,
            @RequestParam("producer") String producer,
            @RequestParam("director") String director,
            @RequestParam("screenwriter") String screenwriter,
            @RequestParam("cinematographer") String cinematographer,
            @RequestParam("genre") String genre,
            @RequestParam("budget") BigDecimal budget,
            @RequestParam("ageRating") String ageRating,
            @RequestParam("duration") Time duration,


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
        AboutMovie aboutMovie = new AboutMovie();

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

        aboutMovie.setYear(year);
        aboutMovie.setComposer(composer);
        aboutMovie.setProducer(producer);
        aboutMovie.setDirector(director);
        aboutMovie.setScreenwriter(screenwriter);
        aboutMovie.setCinematographer(cinematographer);
        aboutMovie.setGenre(genre);
        aboutMovie.setBudget(budget);
        aboutMovie.setAge_rating(ageRating);

        Time sqlTime = Time.valueOf(duration.toLocalTime());
        aboutMovie.setDuration(sqlTime);


        seoBlocks.setUrl(url);
        seoBlocks.setTitle(title);
        seoBlocks.setKeywords(keywords);
        seoBlocks.setDescription(description_seo);

        if (movie_type != null) {
            movies.setMovieType(movie_type);
        }

        movies.setGallery(gallary);
        movies.setSeoBlocks(seoBlocks);
        movies.setAboutMovie(aboutMovie);

        movieRepository.save(movies);
        galleryRepository.save(gallary);
        seoBlocksRepository.save(seoBlocks);
        aboutMovieRepository.save(aboutMovie);

        return "redirect:/admin/films";
    }

    @PostMapping("/admin/films/{id}/remove")
    @PreAuthorize("hasRole('ADMIN')")
    public String removeFilm(@PathVariable(value = "id") long id, Model model) {
        Movies movies = movieRepository.findById(id).orElseThrow();

        // Получите имена файлов изображений из базы данных
        String mainImageFilename = movies.getMain_page_path();

        // Если есть имена файлов изображений, удаляем файлы
        if (mainImageFilename != null && !mainImageFilename.isEmpty()) {
            deleteImage(mainImageFilename);
        }

        // Удаляем фильм из базы данных
        movieRepository.delete(movies);

        return "redirect:/admin/films";
    }

    private void deleteImage(String imageFilename) {
        String uploadPath = "upload";
        Path imagePath = Paths.get(uploadPath, imageFilename);

        try {
            Files.delete(imagePath);
        } catch (IOException e) {
            // Handle the exception, e.g., log an error
            e.printStackTrace();
        }
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