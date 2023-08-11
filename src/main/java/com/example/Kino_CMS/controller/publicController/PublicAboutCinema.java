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
    @Autowired
    private AboutCinemaServiceImpl aboutCinemaServiceImpl;

    @GetMapping("/aboutCinema")
    public String aboutCinema(Model model){
        AboutCinema aboutCinema = aboutCinemaServiceImpl.getAboutCinemaById(1L);// Если запись с id=2 не найдена, то вернем пустой объект MainPage
        model.addAttribute("about_cinema", aboutCinema);
        return "/public/pages/public-about-cinema";
    }
}
