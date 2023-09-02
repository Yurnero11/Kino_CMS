package com.example.Kino_CMS.controller.publicController;

import com.example.Kino_CMS.entity.Promotion;
import com.example.Kino_CMS.service.impl.PromotionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class PublicPromotionsController {
    @Autowired
    private PromotionServiceImpl promotionServiceImpl;


    @GetMapping("/promotions")
    public String promotionsIndex(Model model,
                                  @RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "query", required = false) String query) {

        int pageSize = 2; // Устанавливаем количество акций на страницу
        Page<Promotion> promotionPage = promotionServiceImpl.findAllPage(page, pageSize);
        List<Promotion> promotions = promotionPage.getContent();

        model.addAttribute("promotions", promotions);
        model.addAttribute("currentPage", page);

        int totalActivePromotions = promotionServiceImpl.countActiveNews(); // Метод для подсчета активных акций
        int totalPages = (int) Math.ceil((double) totalActivePromotions / pageSize); // Вычисляем общее количество страниц

        // Проверяем, есть ли достаточно активных акций для отображения пагинации
        boolean showPagination = totalActivePromotions >= 3;
        model.addAttribute("showPagination", showPagination);

        if (showPagination) {
            model.addAttribute("totalPages", totalPages);

            int nextPage = page + 1;

            // Проверяем, если nextPage больше или равно общему количеству страниц, то nextPage устанавливаем в -1
            if (nextPage >= totalPages) {
                nextPage = -1;
            }

            model.addAttribute("nextPage", nextPage);
        }

        return "/public/promotions/promotions-index";
    }

    @GetMapping("/promotions/{promotion_id}")
    public String promotionsPage(@PathVariable(value = "promotion_id") long id,
                                 Model model){
        Optional<Promotion> optionalPromotions = promotionServiceImpl.getPromotionById(id);
        if (optionalPromotions.isEmpty()) {
            return "redirect:/promotions";
        }
        Promotion promotion = optionalPromotions.get();

        model.addAttribute("promotions", promotion);
        return "/public/promotions/promotion-page";
    }
}
