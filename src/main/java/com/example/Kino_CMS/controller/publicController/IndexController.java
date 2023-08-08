package com.example.Kino_CMS.controller.publicController;

import com.example.Kino_CMS.entity.*;
import com.example.Kino_CMS.repository.*;
import com.example.Kino_CMS.service.impl.MainBannerServiceImpl;
import com.example.Kino_CMS.service.impl.MovieServiceImpl;
import com.example.Kino_CMS.service.impl.SessionServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {
    private final PageRepository pageRepository;
    private final MainPageRepository mainPageRepository;
    private final AboutCinemaRepository aboutCinemaRepository;
    private final VipHallRepository vipHallRepository;
    private final AdvertisingRepository advertisingRepository;
    private final KidsRoomRepository kidsRoomRepository;
    private final CinemaContactsRepository contactsRepository;
    private final ContactForTableRepository contactForTableRepository;
    private final CafeBarRepository cafeBarRepository;

    private final BackgroundBannerRepository backgroundBannerRepository;

    private final UserRepository userRepository;

    private final SessionServiceImpl session;

    private final MainBannerServiceImpl mainBannersService;

    private final MovieServiceImpl movieServiceImpl;



    public IndexController(PageRepository pageRepository, MainPageRepository mainPageRepository, AboutCinemaRepository aboutCinemaRepository, VipHallRepository vipHallRepository, AdvertisingRepository advertisingRepository, KidsRoomRepository kidsRoomRepository, CinemaContactsRepository contactsRepository, ContactForTableRepository contactForTableRepository, CafeBarRepository cafeBarRepository, NewsRepository newsRepository, BackgroundBannerRepository backgroundBannerRepository, UserRepository userRepository, SessionServiceImpl session, MainBannerServiceImpl mainBannersService, MovieRepository movieRepository, MovieServiceImpl movieServiceImpl) {
        this.pageRepository = pageRepository;
        this.mainPageRepository = mainPageRepository;
        this.aboutCinemaRepository = aboutCinemaRepository;
        this.vipHallRepository = vipHallRepository;
        this.advertisingRepository = advertisingRepository;
        this.kidsRoomRepository = kidsRoomRepository;
        this.contactsRepository = contactsRepository;
        this.contactForTableRepository = contactForTableRepository;
        this.cafeBarRepository = cafeBarRepository;
        this.backgroundBannerRepository = backgroundBannerRepository;
        this.userRepository = userRepository;
        this.session = session;

        this.mainBannersService = mainBannersService;
        this.movieServiceImpl = movieServiceImpl;

    }

    @GetMapping("/home")
    public String index(Model model, HttpServletRequest request){
        MainBanners latestMainBanner = mainBannersService.getLatestMainBanner();
        model.addAttribute("mainBanner", latestMainBanner);

        Iterable<Movies> movies = movieServiceImpl.getAllMovies();
        model.addAttribute("movies", movies);

        List<Pages> pages = pageRepository.findAll();
        String currentPath = request.getRequestURI();
        model.addAttribute("currentPath", currentPath);
        model.addAttribute("pages", pages);

        List<AboutCinema> aboutCinemas = aboutCinemaRepository.findAll();
        model.addAttribute("about_cinema", aboutCinemas);

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

        return "public/index-page/index";
    }

    @GetMapping("/header")
    public String header(Model model, HttpServletRequest request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            model.addAttribute("username", username);


            // Добавляем id пользователя в модель
            User user = userRepository.findByUsername(username);
            if (user != null) {
                Integer userId = user.getId();
                model.addAttribute("id", userId);
            } else {
                // Обработка случая, когда пользователя с таким именем нет в базе данных
            }
        }
        List<Pages> pages = pageRepository.findAll();
        String currentPath = request.getRequestURI();
        model.addAttribute("currentPath", currentPath);
        model.addAttribute("pages", pages);

        List<AboutCinema> aboutCinemas = aboutCinemaRepository.findAll();
        model.addAttribute("about_cinema", aboutCinemas);

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

        return "public/content/header";
    }

    @GetMapping("/footer")
    public String footer(Model model, HttpServletRequest request){
        List<Pages> pages = pageRepository.findAll();
        String currentPath = request.getRequestURI();
        model.addAttribute("currentPath", currentPath);
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

        return "public/content/footer";
    }
}
