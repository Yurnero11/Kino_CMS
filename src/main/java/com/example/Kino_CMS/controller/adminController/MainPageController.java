package com.example.Kino_CMS.controller.adminController;

import com.example.Kino_CMS.entity.MainPage;
import com.example.Kino_CMS.repository.GalleryRepository;
import com.example.Kino_CMS.repository.MainPageRepository;
import com.example.Kino_CMS.repository.SeoBlocksRepository;
import com.example.Kino_CMS.service.impl.MainPageServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class MainPageController {
    private final GalleryRepository galleryRepository;
    private final SeoBlocksRepository seoBlocksRepository;
    private final MainPageRepository mainPageRepository;

    private final MainPageServiceImpl mainPageServiceImpl;

    public MainPageController(GalleryRepository galleryRepository, SeoBlocksRepository seoBlocksRepository, MainPageRepository mainPageRepository, MainPageServiceImpl mainPageServiceImpl) {
        this.galleryRepository = galleryRepository;
        this.seoBlocksRepository = seoBlocksRepository;
        this.mainPageRepository = mainPageRepository;
        this.mainPageServiceImpl = mainPageServiceImpl;
    }

    @GetMapping("/admin/pages/main-page")
    public String mainPage(Model model) {
        // Получаем объект MainPage из базы данных по id (например, id=2)
        MainPage mainPage = mainPageServiceImpl.getMainPageById(2L); // Если запись с id=2 не найдена, то вернем пустой объект MainPage

        model.addAttribute("main_page", mainPage);
        return "admin/pages/main-page";
    }

    @PostMapping("/admin/pages/main-page")
    public String mainPageEdit(@ModelAttribute("phone_number_1") String phone_number_1,
                               @ModelAttribute("phone_number_2") String phone_number_2,
                               @RequestParam("seo_text") String seo_text,
                               @RequestParam(value = "status", required = false, defaultValue = "off") String status,
                               @RequestParam("url") String url,
                               @RequestParam("title") String title,
                               @RequestParam("keywords") String keywords,
                               @RequestParam("description_seo") String descriptionSeo,
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
            currentPage.setPhone_number_1(phone_number_1);
            currentPage.setPhone_number_2(phone_number_2);
            currentPage.setSeo_text(seo_text);

            if ("on".equals(status)) {
                // Обработка, когда статус включен
                currentPage.setStatus("on");
            } else {
                // Обработка, когда статус выключен или отсутствует
                currentPage.setStatus("off");
            }

            currentPage.setSeo_url(url);
            currentPage.setSeo_title(title);
            currentPage.setSeo_keywords(keywords);
            currentPage.setSeo_description(descriptionSeo);

            // Сохраните обновленный объект MainPage в базе данных.
            mainPageServiceImpl.saveMainPage(currentPage);

            // Вы можете добавить атрибуты Flash для отображения сообщений об успешном обновлении, если хотите.
            redirectAttributes.addFlashAttribute("successMessage", "Данные успешно обновлены.");
        } else {
            // Если объект MainPage с заданным id не найден, выполните соответствующие действия.
            // Например, можно создать новый объект и сохранить его, если это приемлемо.
            // Или вы можете выбрать другую логику обработки в этом случае.
        }

        return "redirect:/admin/pages";
    }
}
