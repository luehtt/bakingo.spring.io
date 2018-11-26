package io.bakingo.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping("/admin")
    public String admin(){
        return "redirect:/admin/order";
    }

    @GetMapping("/user")
    public String user(){
        return "redirect:/user/cart";
    }
}
