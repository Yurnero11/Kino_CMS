package com.example.Kino_CMS.controller.adminController;

import com.example.Kino_CMS.entity.*;
import com.example.Kino_CMS.repository.*;
import com.example.Kino_CMS.service.impl.PageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PagesController {
    @Autowired
    private  PageRepository pageRepository;
    @Autowired
    private  MainPageRepository mainPageRepository;
    @Autowired
    private  AboutCinemaRepository aboutCinemaRepository;
    @Autowired
    private  VipHallRepository vipHallRepository;
    @Autowired
    private  AdvertisingRepository advertisingRepository;
    @Autowired
    private  KidsRoomRepository kidsRoomRepository;
    @Autowired
    private  ContactForTableRepository contactForTableRepository;
    @Autowired
    private  CafeBarRepository cafeBarRepository;


    @GetMapping("/admin/pages")
    public String pages(Model model){
        List<Pages> pages = pageRepository.findAll();
        model.addAttribute("pages", pages);

        List<AboutCinema> aboutCinemas = aboutCinemaRepository.findAll();
        model.addAttribute("about_cinema", aboutCinemas);

        List<CafeBar> cafeBars = cafeBarRepository.findAll();
        model.addAttribute("cafe_bar", cafeBars);

        List<MainPage> mainPages = mainPageRepository.findAll();
        model.addAttribute("mainPages", mainPages);

        List<VipHall> vipHalls = vipHallRepository.findAll();
        model.addAttribute("vip_halls", vipHalls);

        List<Advertising> advertising = advertisingRepository.findAll();
        model.addAttribute("advertising", advertising);

        List<KidsRoom> kidsRooms = kidsRoomRepository.findAll();
        model.addAttribute("kidsroom", kidsRooms);

        List<Contact_for_table> contactForTables = contactForTableRepository.findAll();
        model.addAttribute("cinemaContactsForTable", contactForTables);

        return "/admin/pages/pages";
    }
}