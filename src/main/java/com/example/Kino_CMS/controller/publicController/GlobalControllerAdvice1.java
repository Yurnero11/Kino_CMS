package com.example.Kino_CMS.controller.publicController;

import com.example.Kino_CMS.entity.*;
import com.example.Kino_CMS.repository.SeoBlockCinemaContactRepository;
import com.example.Kino_CMS.service.impl.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Optional;

@ControllerAdvice
public class GlobalControllerAdvice1 {
    @Autowired
    private AboutCinemaServiceImpl aboutCinemaService;
    @Autowired
    private PageServiceImpl pageService;
    @Autowired
    private MainPageServiceImpl mainPageService;
    @Autowired
    private CafeBarServiceImpl cafeBarService;
    @Autowired
    private VipHallServiceImpl vipHallService;
    @Autowired
    private AdvertisingServiceImpl advertisingService;
    @Autowired
    private KidsRoomServiceImpl kidsRoomService;
    @Autowired
    private ContactForTableServiceImpl contactForTableService;
    @Autowired
    private SeoBlockCinemaContactServiceImpl seoBlockCinemaContactService;
    @Autowired
    private SeoBlockCinemaContactRepository seoBlockCinemaContactRepository;

    @ModelAttribute
    public void addCommonAttributes(Model model, HttpServletRequest request, Authentication authentication1) {
        boolean isAuthenticated = authentication1 != null && authentication1.isAuthenticated();
        model.addAttribute("authenticated", isAuthenticated);

        Iterable<AboutCinema> aboutCinemas = aboutCinemaService.getAllAboutCinema();
        model.addAttribute("about_cinema", aboutCinemas);

        Iterable<Page> pages = pageService.getAllPages();
        String currentPath = request.getRequestURI();
        model.addAttribute("currentPath", currentPath);
        model.addAttribute("pages", pages);

        Iterable<MainPage> mainPages = mainPageService.getAllMainPages();
        model.addAttribute("mainPages", mainPages);

        Iterable<CafeBar> cafeBars = cafeBarService.getAllCafeBars();
        model.addAttribute("cafe_bar", cafeBars);

        Iterable<VipHall> vipHalls = vipHallService.getAllVipHals();
        model.addAttribute("vip_halls", vipHalls);

        Iterable<Advertising> advertising = advertisingService.getAllAdvertising();
        model.addAttribute("advertising", advertising);

        Iterable<KidsRoom> kidsRooms = kidsRoomService.getAllKidsRoom();
        model.addAttribute("kidsroom", kidsRooms);

        Iterable<Contact_for_table> contactForTables = contactForTableService.getAllContacts();
        model.addAttribute("cinemaContactsForTable", contactForTables);

        List<SeoBlockCinemaContact> seoBlockContacts = seoBlockCinemaContactRepository.findAll();
        model.addAttribute("seoBlockContacts", seoBlockContacts);
    }
}
