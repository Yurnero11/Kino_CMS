package com.example.Kino_CMS.controller.publicController;

import com.example.Kino_CMS.entity.Promotions;
import com.example.Kino_CMS.repository.PromotionRepository;
import com.example.Kino_CMS.service.impl.PromotionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class PublicPromotionsController {
    private final PromotionRepository promotionRepository;
    private final PromotionServiceImpl promotionServiceImpl;

    @Autowired
    public PublicPromotionsController(PromotionRepository promotionRepository, PromotionServiceImpl promotionServiceImpl) {
        this.promotionRepository = promotionRepository;
        this.promotionServiceImpl = promotionServiceImpl;
    }

    @GetMapping("/promotions")
    public String promotionsIndex(Model model){
        Iterable<Promotions> promotions = promotionServiceImpl.getAllPromotions(); // Здесь используйте ваш сервис или репозиторий для получения списка кинотеатров
        model.addAttribute("promotions", promotions);
        return "/public/promotions/promotions-index";
    }

    @GetMapping("/promotions/{promotion_id}")
    public String promotionsPage(@PathVariable(value = "promotion_id") long id,
                                 Model model){
        Optional<Promotions> optionalPromotions = promotionRepository.findById(id);
        if (optionalPromotions.isEmpty()) {
            return "redirect:/promotions";
        }
        Promotions promotions = optionalPromotions.get();

        model.addAttribute("promotions", promotions);
        return "/public/promotions/promotion-page";
    }
}
