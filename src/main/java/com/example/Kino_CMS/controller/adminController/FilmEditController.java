package com.example.Kino_CMS.controller.adminController;

import com.example.Kino_CMS.entity.Movie;
import com.example.Kino_CMS.repository.MovieRepository;
import com.example.Kino_CMS.service.impl.MovieServiceImpl;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BeanPropertyBindingResult;
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
import java.sql.Time;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class FilmEditController {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieServiceImpl service;

    @GetMapping("/admin/films/{movie_id}/edit")
    public String editFilm(@PathVariable(value = "movie_id") long id, Model model) {
        if (!movieRepository.existsById(id)) {
            return "redirect:/admin/films";
        }

        Optional<Movie> optionalMovies = movieRepository.findById(id);
        if (optionalMovies.isEmpty()) {
            return "redirect:/admin/cinemas";
        }

        Movie movie = optionalMovies.get();

        // Додати об'єкт cinema в модель
        model.addAttribute("movies", movie);

        // Додати об'єкт BindingResult для зберігання помилок валідації
        model.addAttribute("bindingResult", new BeanPropertyBindingResult(movie, "movies"));

        return "admin/films/film-edit";
    }

    @PostMapping("/admin/films/{movie_id}/edit")
    public String editFilmSubmit(@PathVariable(value = "movie_id") long id,
                                 @RequestParam("name") String name,
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


                                 @RequestParam("trailer_url") String trailer_url,
                                 @RequestParam("movie_data") String movie_data,
                                 @RequestParam(name = "movie_type", required = false) List<String> movieTypes,
                                 @RequestParam("seoBlock.url") String link,
                                 @RequestParam("seoBlock.title") String title,
                                 @RequestParam("seoBlock.keywords") String keywords,
                                 @RequestParam("seoBlock.description") String description_seo,
                                 ServletContext servletContext,
                                 RedirectAttributes redirectAttributes) {

        Optional<Movie> optionalMovies = service.getMovieById(id);
        if (optionalMovies.isEmpty()) {
            return "redirect:/admin/films";
        }

        Movie movie = optionalMovies.get();
        movie.setName(name);
        movie.setDescription(description);
        movie.setMain_page_path(saveImage(upload, movie.getMain_page_path()));


        movie.getGallery().setImagePath1(saveImage(upload1, movie.getGallery().getImagePath1()));
        movie.getGallery().setImagePath2(saveImage(upload2, movie.getGallery().getImagePath2()));
        movie.getGallery().setImagePath3(saveImage(upload3, movie.getGallery().getImagePath3()));
        movie.getGallery().setImagePath4(saveImage(upload4, movie.getGallery().getImagePath4()));
        movie.getGallery().setImagePath5(saveImage(upload5, movie.getGallery().getImagePath5()));

        movie.setMovie_data(movie_data);
        movie.setTrailer_url(trailer_url);

        if (movieTypes != null && !movieTypes.isEmpty()) {
            String movieTypeStr = String.join(", ", movieTypes);
            movie.setMovieType(movieTypeStr);
        }

        movie.getAboutMovie().setYear(year);
        movie.getAboutMovie().setComposer(composer);
        movie.getAboutMovie().setProducer(producer);
        movie.getAboutMovie().setDirector(director);
        movie.getAboutMovie().setScreenwriter(screenwriter);
        movie.getAboutMovie().setCinematographer(cinematographer);
        movie.getAboutMovie().setGenre(genre);
        movie.getAboutMovie().setBudget(budget);
        movie.getAboutMovie().setAge_rating(ageRating);

        Time sqlTime = Time.valueOf(duration.toLocalTime());
        movie.getAboutMovie().setDuration(sqlTime);

        movie.getSeoBlock().setUrl(link);
        movie.getSeoBlock().setTitle(title);
        movie.getSeoBlock().setKeywords(keywords);
        movie.getSeoBlock().setDescription(description_seo);

        service.saveMovies(movie);

        return "redirect:/admin/films";
    }

    @Value("${spring.pathImg}")
    private String pathPhotos;

    private String saveImage(MultipartFile file, String currentImagePath) {
        if (file != null && !file.isEmpty()) {
            try {
                String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
                String fileExtension = getFileExtension(originalFileName);
                String uniqueFileName = generateUniqueFileName(fileExtension);

                Path filePath = Paths.get(pathPhotos, uniqueFileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                return uniqueFileName;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return currentImagePath; // Возвращаем старое значение
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
