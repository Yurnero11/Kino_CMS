package com.example.Kino_CMS.controller.publicController;

import com.example.Kino_CMS.entity.*;
import com.example.Kino_CMS.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice1 {
    private final PageRepository pageRepository;
    private final AboutCinemaRepository aboutCinemaRepository;
    private final MainPageRepository mainPageRepository;
    private final VipHallRepository vipHallRepository;
    private final AdvertisingRepository advertisingRepository;
    private final KidsRoomRepository kidsRoomRepository;
    private final ContactForTableRepository contactForTableRepository;
    private final CafeBarRepository cafeBarRepository;

    public GlobalControllerAdvice1(
            PageRepository pageRepository,
            AboutCinemaRepository aboutCinemaRepository,
            MainPageRepository mainPageRepository,
            VipHallRepository vipHallRepository,
            AdvertisingRepository advertisingRepository,
            KidsRoomRepository kidsRoomRepository,
            ContactForTableRepository contactForTableRepository, CafeBarRepository cafeBarRepository) {
        this.pageRepository = pageRepository;
        this.aboutCinemaRepository = aboutCinemaRepository;
        this.mainPageRepository = mainPageRepository;
        this.vipHallRepository = vipHallRepository;
        this.advertisingRepository = advertisingRepository;
        this.kidsRoomRepository = kidsRoomRepository;

        this.contactForTableRepository = contactForTableRepository;
        this.cafeBarRepository = cafeBarRepository;
    }

    @ModelAttribute
    public void addCommonAttributes(Model model, HttpServletRequest request) {
        List<AboutCinema> aboutCinemas = aboutCinemaRepository.findAll();
        model.addAttribute("about_cinema", aboutCinemas);

        List<Pages> pages = pageRepository.findAll();
        String currentPath = request.getRequestURI();
        model.addAttribute("currentPath", currentPath);
        model.addAttribute("pages", pages);

        List<MainPage> mainPages = mainPageRepository.findAll();
        model.addAttribute("mainPages", mainPages);

        List<CafeBar> cafeBars = cafeBarRepository.findAll();
        model.addAttribute("cafe_bar", cafeBars);

        List<VipHall> vipHalls = vipHallRepository.findAll();
        model.addAttribute("vip_halls", vipHalls);

        List<Advertising> advertising = advertisingRepository.findAll();
        model.addAttribute("advertising", advertising);

        List<KidsRoom> kidsRooms = kidsRoomRepository.findAll();
        model.addAttribute("kidsroom", kidsRooms);

        List<Contact_for_table> contactForTables = contactForTableRepository.findAll();
        model.addAttribute("cinemaContactsForTable", contactForTables);
    }
}
