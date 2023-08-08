package com.example.Kino_CMS.controller.publicController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScheduleController {
    @GetMapping("/schedule")
    public String schedule(Model model){
        return "/public/schedule/schedule-page";
    }

    @GetMapping("/schedule/ticket")
    public String scheduleTicket(Model model){
        return "/public/schedule/schedule-ticket";
    }
}
