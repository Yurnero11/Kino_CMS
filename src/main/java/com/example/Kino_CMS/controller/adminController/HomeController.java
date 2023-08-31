package com.example.Kino_CMS.controller.adminController;

import com.example.Kino_CMS.entity.Session;
import com.example.Kino_CMS.repository.MovieRepository;
import com.example.Kino_CMS.repository.UserRepository;
import com.example.Kino_CMS.service.impl.SessionVisitorsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SessionVisitorsServiceImp sessionVisitorsServiceImp;

    @GetMapping("/admin/home")
    public String home(Model model){
        long userCount = userRepository.count(); // Подсчет количества пользователей из репозитория
        model.addAttribute("userCount", userCount);


        long maleCount = userRepository.countByGender("male"); // Здесь предполагается, что у вас есть UserRepository для доступа к базе данных
        long femaleCount = userRepository.countByGender("female");
        model.addAttribute("maleCount", maleCount);
        model.addAttribute("femaleCount", femaleCount);


        long movieCount = movieRepository.count();
        model.addAttribute("movieCount", movieCount);

        long nowMovie = movieRepository.countMoviesByMovieData("now");
        long soonMovie = movieRepository.countMoviesByMovieData("soon");
        model.addAttribute("nowMovie", nowMovie);
        model.addAttribute("soonMovie", soonMovie);

        String currentDay = LocalDate.now().toString();
        // Увеличьте счетчик посещений для текущего дня
        sessionVisitorsServiceImp.incrementVisits(currentDay);
        // Получите все сессии и передайте их в представление
        List<Session> sessions = sessionVisitorsServiceImp.getAllSessions();
        model.addAttribute("sessions", sessions);


        return "admin/dashboard/index";
    }
}
