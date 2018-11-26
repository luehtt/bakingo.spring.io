package io.bakingo.demo.controller;

import io.bakingo.demo.model.Item;
import io.bakingo.demo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShopController {

    private final ItemService itemService;

    @Autowired
    public ShopController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping({"", "/shop"})
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("shop/index");
        return modelAndView;
    }

    @GetMapping("/shop/{id}")
    public ModelAndView item(@PathVariable int id){
        ModelAndView modelAndView = new ModelAndView();
        try {
            Item item = itemService.findById(id);
            modelAndView.addObject("item", item);
            modelAndView.setViewName("shop/item");
        } catch(Exception e) {
            modelAndView.setViewName("redirect:/shop");
        }
        return modelAndView;

    }
}
