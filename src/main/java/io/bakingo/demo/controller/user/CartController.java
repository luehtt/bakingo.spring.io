package io.bakingo.demo.controller.user;

import io.bakingo.demo.model.Order;
import io.bakingo.demo.model.User;
import io.bakingo.demo.service.OrderService;
import io.bakingo.demo.service.PrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user/cart")
public class CartController {

    private final OrderService orderService;
    private final PrincipalService principalService;

    @Autowired
    public CartController(OrderService orderService, PrincipalService principalService) {
        this.orderService = orderService;
        this.principalService = principalService;
    }

    @GetMapping({"", "/index"})
    public ModelAndView all() {
        User user = principalService.getUser();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("user/cart/index");
        return modelAndView;
    }

    @PostMapping("/order")
    public ModelAndView save(@ModelAttribute("user") User user, RedirectAttributes redirect,
                             @RequestParam("item_id[]") Integer[] itemId,
                             @RequestParam("item_amount[]") Integer[] amount) {
        try {
            Order item = orderService.save(user, itemId, amount);
            redirect.addFlashAttribute("success", "true");
            redirect.addFlashAttribute("message", "Order is created");

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("user", user);
            modelAndView.addObject("order", item);
            modelAndView.addObject("success", true);
            modelAndView.setViewName("user/cart/result");
            return modelAndView;
        } catch(Exception e) {
            ModelAndView modelAndView = new ModelAndView();
            redirect.addFlashAttribute("success", "false");
            redirect.addFlashAttribute("message", e);
            modelAndView.setViewName("redirect:/user/cart");
            return modelAndView;
        }
    }

}
