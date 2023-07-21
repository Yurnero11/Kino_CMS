package com.example.Kino_CMS.controller;

import com.example.Kino_CMS.entity.MainPage;
import com.example.Kino_CMS.repository.GalleryRepository;
import com.example.Kino_CMS.repository.MainPageRepository;
import com.example.Kino_CMS.repository.SeoBlocksRepository;
import com.example.Kino_CMS.service.MainPageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class MainPageController {
    private final GalleryRepository galleryRepository;
    private final SeoBlocksRepository seoBlocksRepository;
    private final MainPageRepository mainPageRepository;

    private final MainPageService mainPageService;

    public MainPageController(GalleryRepository galleryRepository, SeoBlocksRepository seoBlocksRepository, MainPageRepository mainPageRepository, MainPageService mainPageService) {
        this.galleryRepository = galleryRepository;
        this.seoBlocksRepository = seoBlocksRepository;
        this.mainPageRepository = mainPageRepository;
        this.mainPageService = mainPageService;
    }

    @GetMapping("/pages/main-page")
    public String mainPage(Model model) {
        // Получаем объект MainPage из базы данных по id (например, id=2)
        MainPage mainPage = mainPageRepository.findById(2L).orElse(new MainPage()); // Если запись с id=2 не найдена, то вернем пустой объект MainPage

        model.addAttribute("main_page", mainPage);
        return "pages/main-page";
    }

    @PostMapping("/pages/main-page")
    public String mainPageEdit(@ModelAttribute("main_page") MainPage mainPage,
                               RedirectAttributes redirectAttributes) {

        // Здесь предполагается, что объект MainPage уже содержит данные из формы,
        // переданные через th:object в представлении.

        // Выполните поиск существующего объекта MainPage по его id в базе данных.
        // Если объект не найден, можно выполнить какие-то дополнительные действия,
        // например, создать новый объект, если это приемлемо в вашем случае.
        Optional<MainPage> existingMainPage = mainPageRepository.findById(2L);

        if (existingMainPage.isPresent()) {
            MainPage currentPage = existingMainPage.get();

            // Обновите поля существующего объекта MainPage данными из формы.
            currentPage.setPhone_number_1(mainPage.getPhone_number_1());
            currentPage.setPhone_number_2(mainPage.getPhone_number_2());
            currentPage.setSeo_text(mainPage.getSeo_text());
            currentPage.setStatus(mainPage.getStatus());
            currentPage.setSeo_url(mainPage.getSeo_url());
            currentPage.setSeo_title(mainPage.getSeo_title());
            currentPage.setSeo_keywords(mainPage.getSeo_keywords());
            currentPage.setSeo_description(mainPage.getSeo_description());

            // Сохраните обновленный объект MainPage в базе данных.
            mainPageRepository.save(currentPage);

            // Вы можете добавить атрибуты Flash для отображения сообщений об успешном обновлении, если хотите.
            redirectAttributes.addFlashAttribute("successMessage", "Данные успешно обновлены.");
        } else {
            // Если объект MainPage с заданным id не найден, выполните соответствующие действия.
            // Например, можно создать новый объект и сохранить его, если это приемлемо.
            // Или вы можете выбрать другую логику обработки в этом случае.
        }

        return "redirect:/pages";
    }

}
