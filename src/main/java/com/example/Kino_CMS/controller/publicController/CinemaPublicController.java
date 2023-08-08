package com.example.Kino_CMS.controller.publicController;

import com.example.Kino_CMS.entity.Cinemas;
import com.example.Kino_CMS.entity.Gallary;
import com.example.Kino_CMS.entity.Halls;
import com.example.Kino_CMS.repository.CinemaRepository;
import com.example.Kino_CMS.repository.HallRepository;
import com.example.Kino_CMS.service.impl.CinemaServiceImpl;
import com.example.Kino_CMS.service.impl.HallServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CinemaPublicController {
    private final CinemaServiceImpl service;
    private final CinemaRepository cinemaRepository;
    private final HallRepository hallRepository;
    private final HallServiceImpl hallService;

    public CinemaPublicController(CinemaServiceImpl service, CinemaRepository cinemaRepository, HallRepository hallRepository, HallServiceImpl hallService) {
        this.service = service;
        this.cinemaRepository = cinemaRepository;
        this.hallRepository = hallRepository;
        this.hallService = hallService;
    }

    @GetMapping("/cinemas")
    public String cinemas(Model model){
        Iterable<Cinemas> cinemas = service.getAllCinemas(); // Здесь используйте ваш сервис или репозиторий для получения списка кинотеатров
        model.addAttribute("cinemas", cinemas);
        return "/public/cinema/cinemas-page";
    }

    @GetMapping("/cinema/{cinema_id}")
    public String cinemaPage(@PathVariable(value = "cinema_id") long id, Model model){
        Optional<Cinemas> optionalCinemas = cinemaRepository.findById(id);
        if (optionalCinemas.isEmpty()) {
            return "redirect:/cinemas";
        }

        Cinemas cinemas = optionalCinemas.get();
        // Retrieve the list of halls associated with the cinema
        List<Halls> hallsList = cinemas.getHalls();
        // Add the halls list to the model
        model.addAttribute("hallsList", hallsList);

        Gallary gallery = service.getGalleryByCinemaId((long) cinemas.getCinema_id());
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
        model.addAttribute("cinemas", cinemas);
        return "/public/cinema/cinema-id";
    }

    @GetMapping("/cinema/{cinema_id}/hall/{hall_id}")
    public String hallPage(@PathVariable("cinema_id") Long cinemaId,
                           @PathVariable("hall_id") Long hallId,
                           Model model)
    {
        Optional<Halls> optionalHalls = hallRepository.findById(hallId);
        if (optionalHalls.isEmpty()) {
            return "redirect:/cinemas";
        }

        Halls  halls = optionalHalls.get();


        Gallary gallery = hallService.getGalleryByHallId((long) halls.getHall_id());
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
        model.addAttribute("halls", halls);
        return "/public/cinema/hall-page";
    }

}
