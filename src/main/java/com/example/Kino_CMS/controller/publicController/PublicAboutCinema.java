package com.example.Kino_CMS.controller.publicController;

import com.example.Kino_CMS.entity.AboutCinema;
import com.example.Kino_CMS.repository.AboutCinemaRepository;
import com.example.Kino_CMS.service.impl.AboutCinemaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicAboutCinema {
    private final AboutCinemaRepository aboutCinemaRepository;
    private final AboutCinemaServiceImpl aboutCinemaServiceImpl;

    @Autowired
    public PublicAboutCinema(AboutCinemaRepository aboutCinemaRepository, AboutCinemaServiceImpl aboutCinemaServiceImpl) {
        this.aboutCinemaRepository = aboutCinemaRepository;
        this.aboutCinemaServiceImpl = aboutCinemaServiceImpl;
    }

    @GetMapping("/aboutCinema")
    public String aboutCinema(Model model){
        AboutCinema aboutCinema = aboutCinemaRepository.findById(1L).orElse(new AboutCinema()); // Если запись с id=2 не найдена, то вернем пустой объект MainPage
        model.addAttribute("about_cinema", aboutCinema);
        return "/public/pages/public-about-cinema";
    }
}
