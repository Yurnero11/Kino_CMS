package com.example.Kino_CMS.controller.publicController;

import com.example.Kino_CMS.entity.*;
import com.example.Kino_CMS.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class PublicPagesController {
    @Autowired
    private CafeBarServiceImpl cafeBarService;
    @Autowired
    private VipHallServiceImpl vipHallService;
    @Autowired
    private AdvertisingServiceImpl advertisingService;
    @Autowired
    private KidsRoomServiceImpl kidsRoomService;
    @Autowired
    private CinemaContactsServiceImpl cinemaContactsService;
    @Autowired
    private PageServiceImpl pageService;


    @GetMapping("/cafeBar")
    public String cafeBar(Model model){
        CafeBar cafeBar = cafeBarService.getCafeBarById(1L); // Если запись с id=2 не найдена, то вернем пустой объект MainPage
        model.addAttribute("cafe_bar", cafeBar);
        return "/public/pages/cafe-bar";
    }

    @GetMapping("/vipHall")
    public String vipHall(Model model){
        VipHall vipHall = vipHallService.findById(1L).orElse(new VipHall()); // Если запись с id=2 не найдена, то вернем пустой объект MainPage
        model.addAttribute("vip_hall", vipHall);
        return "/public/pages/viphall-page";
    }

    @GetMapping("/advertising")
    public String advertisingPage(Model model){
        Advertising advertising = advertisingService.getAdvertisingById(1L).orElse(new Advertising()); // Если запись с id=2 не найдена, то вернем пустой объект MainPage
        model.addAttribute("advertising_page", advertising);
        return "/public/pages/advertising-page";
    }

    @GetMapping("/kidsRoom")
    public String kidsRoom(Model model){
        KidsRoom kidsRoom = kidsRoomService.getKidsRoomById(1L).orElse(new KidsRoom()); // Если запись с id=2 не найдена, то вернем пустой объект MainPage
        model.addAttribute("kidsRoom_page", kidsRoom);
        return "/public/pages/kidsroom-page";
    }

    @GetMapping("/contacts")
    public String contacts(Model model){
        Iterable<CinemaContact> contactsList = cinemaContactsService.getAllCinemaContact();
        model.addAttribute("contactsList", contactsList);

        // Проход по всем контактам и преобразование координат в десятичные градусы
        for (CinemaContact contact : contactsList) {
            double latitudeString = contact.getCoordinates1();
            double longitudeString = contact.getCoordinates2();

            double latitude = latitudeString;
            double longitude = longitudeString;

            // Добавляем новые атрибуты с десятичными градусами в модель
            model.addAttribute("latitude" + contact.getCinema_id(), latitude);
            model.addAttribute("longitude" + contact.getCinema_id(), longitude);
        }

        return "/public/pages/contacts";
    }

    @GetMapping("/page/{page_id}")
    public String page(Model model, @PathVariable(value = "page_id") long id){
        Optional<Page> pagesOptional = pageService.findById(id);
        if (pagesOptional.isPresent()) {
            Page page = pagesOptional.get();
            model.addAttribute("pages", page);
            return "/public/pages/other-page";
        } else {
            return "redirect:/public/index-page/index";
        }
    }

}
