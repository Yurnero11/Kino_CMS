package com.example.Kino_CMS.controller.adminController;

import com.example.Kino_CMS.entity.*;
import com.example.Kino_CMS.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PagesController {
    private final PageRepository pageRepository;
    private final MainPageRepository mainPageRepository;
    private final AboutCinemaRepository aboutCinemaRepository;
    private final VipHallRepository vipHallRepository;
    private final AdvertisingRepository advertisingRepository;
    private final KidsRoomRepository kidsRoomRepository;
    private final CinemaContactsRepository contactsRepository;
    private final ContactForTableRepository contactForTableRepository;
    private final CafeBarRepository cafeBarRepository;

    public PagesController(PageRepository pageRepository, MainPageRepository mainPageRepository, AboutCinemaRepository aboutCinemaRepository, VipHallRepository vipHallRepository, AdvertisingRepository advertisingRepository, KidsRoomRepository kidsRoomRepository, CinemaContactsRepository contactsRepository, ContactForTableRepository contactForTableRepository, CafeBarRepository cafeBarRepository) {
        this.pageRepository = pageRepository;
        this.mainPageRepository = mainPageRepository;
        this.aboutCinemaRepository = aboutCinemaRepository;
        this.vipHallRepository = vipHallRepository;
        this.advertisingRepository = advertisingRepository;
        this.kidsRoomRepository = kidsRoomRepository;
        this.contactsRepository = contactsRepository;
        this.contactForTableRepository = contactForTableRepository;
        this.cafeBarRepository = cafeBarRepository;
    }

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