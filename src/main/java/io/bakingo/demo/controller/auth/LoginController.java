package io.bakingo.demo.controller.auth;

import io.bakingo.demo.service.PrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    private final PrincipalService principalService;

    @Autowired
    public LoginController(PrincipalService principalService) {
        this.principalService = principalService;
    }

    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("auth/login");
        return modelAndView;
    }

    @GetMapping("/login/default")
    public String defaultLogin() {
        if (principalService.hasRole("ADMIN")) return "redirect:/admin";
        if (principalService.hasRole("USER")) return "redirect:/user";
        return "redirect:/shop";
    }
}
