package com.example.Kino_CMS.controller.publicController;

import com.example.Kino_CMS.entity.Promotion;
import com.example.Kino_CMS.service.impl.PromotionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class PublicPromotionsController {
    @Autowired
    private PromotionServiceImpl promotionServiceImpl;


    @GetMapping("/promotions")
    public String promotionsIndex(Model model){
        Iterable<Promotion> promotions = promotionServiceImpl.getAllPromotions(); // Здесь используйте ваш сервис или репозиторий для получения списка кинотеатров
        model.addAttribute("promotions", promotions);
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
