package com.example.Kino_CMS.controller.publicController;

import com.example.Kino_CMS.entity.*;
import com.example.Kino_CMS.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class PublicPagesController {
    private final CafeBarRepository cafeBarRepository;
    private final VipHallRepository vipHallRepository;
    private final AdvertisingRepository advertisingRepository;
    private final KidsRoomRepository kidsRoomRepository;
    private final CinemaContactsRepository contactsRepository;
    private final PageRepository pageRepository;

    @Autowired
    public PublicPagesController(CafeBarRepository cafeBarRepository, VipHallRepository vipHallRepository, AdvertisingRepository advertisingRepository, KidsRoomRepository kidsRoomRepository, CinemaContactsRepository contactsRepository, PageRepository pageRepository) {
        this.cafeBarRepository = cafeBarRepository;
        this.vipHallRepository = vipHallRepository;
        this.advertisingRepository = advertisingRepository;
        this.kidsRoomRepository = kidsRoomRepository;
        this.contactsRepository = contactsRepository;
        this.pageRepository = pageRepository;
    }

    @GetMapping("/cafeBar")
    public String cafeBar(Model model){
        CafeBar cafeBar = cafeBarRepository.findById(1L).orElse(new CafeBar()); // Если запись с id=2 не найдена, то вернем пустой объект MainPage
        model.addAttribute("cafe_bar", cafeBar);
        return "/public/pages/cafe-bar";
    }

    @GetMapping("/vipHall")
    public String vipHall(Model model){
        VipHall vipHall = vipHallRepository.findById(1L).orElse(new VipHall()); // Если запись с id=2 не найдена, то вернем пустой объект MainPage
        model.addAttribute("vip_hall", vipHall);
        return "/public/pages/viphall-page";
    }

    @GetMapping("/advertising")
    public String advertisingPage(Model model){
        Advertising advertising = advertisingRepository.findById(1L).orElse(new Advertising()); // Если запись с id=2 не найдена, то вернем пустой объект MainPage
        model.addAttribute("advertising_page", advertising);
        return "/public/pages/advertising-page";
    }

    @GetMapping("/kidsRoom")
    public String kidsRoom(Model model){
        KidsRoom kidsRoom = kidsRoomRepository.findById(1L).orElse(new KidsRoom()); // Если запись с id=2 не найдена, то вернем пустой объект MainPage
        model.addAttribute("kidsRoom_page", kidsRoom);
        return "/public/pages/kidsroom-page";
    }

    @GetMapping("/mobile_app")
    public String mobile_app(){
        return "/public/pages/mobile-page";
    }

    @GetMapping("/contacts")
    public String contacts(Model model){
        Iterable<CinemaContacts> contactsList = contactsRepository.findAll();
        model.addAttribute("contactsList", contactsList);

        // Проход по всем контактам и преобразование координат в десятичные градусы
        for (CinemaContacts contact : contactsList) {
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
        Optional<Pages> pagesOptional = pageRepository.findById(id);
        if (pagesOptional.isPresent()) {
            Pages page = pagesOptional.get();
            model.addAttribute("pages", page);
            return "/public/pages/other-page";
        } else {
            return "redirect:/public/index-page/index";
        }
    }

}
