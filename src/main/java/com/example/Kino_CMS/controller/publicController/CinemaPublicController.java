package com.example.Kino_CMS.controller.publicController;

import com.example.Kino_CMS.entity.Cinema;
import com.example.Kino_CMS.entity.Gallary;
import com.example.Kino_CMS.entity.Hall;
import com.example.Kino_CMS.service.impl.CinemaServiceImpl;
import com.example.Kino_CMS.service.impl.HallServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CinemaPublicController {
    @Autowired
    private  CinemaServiceImpl service;
    @Autowired
    private  HallServiceImpl hallService;

    @GetMapping("/cinemas")
    public String cinemas(Model model){
        Iterable<Cinema> cinemas = service.getAllCinemas(); // Здесь используйте ваш сервис или репозиторий для получения списка кинотеатров
        model.addAttribute("cinemas", cinemas);
        return "/public/cinema/cinemas-page";
    }

    @GetMapping("/cinemas/{cinema_id}")
    public String cinemaPage(@PathVariable(value = "cinema_id") long id, Model model){
        Optional<Cinema> optionalCinemas = service.getCinemaById(id);
        if (optionalCinemas.isEmpty()) {
            return "redirect:/cinemas";
        }

        Cinema cinema = optionalCinemas.get();
        // Retrieve the list of halls associated with the cinema
        List<Hall> hallList = cinema.getHalls();
        // Add the halls list to the model
        model.addAttribute("hallsList", hallList);

        Gallary gallery = service.getGalleryByCinemaId((long) cinema.getCinema_id());
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
        model.addAttribute("cinemas", cinema);
        return "/public/cinema/cinema-id";
    }

    @GetMapping("/cinemas/{cinema_id}/hall/{hall_id}")
    public String hallPage(@PathVariable("cinema_id") Long cinemaId,
                           @PathVariable("hall_id") Long hallId,
                           Model model)
    {
        Optional<Hall> optionalHalls = hallService.getHallById(hallId);
        if (optionalHalls.isEmpty()) {
            return "redirect:/cinemas";
        }

        Hall hall = optionalHalls.get();


        Gallary gallery = hallService.getGalleryByHallId((long) hall.getHall_id());
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
        model.addAttribute("halls", hall);
        return "/public/cinema/hall-page";
    }

}
