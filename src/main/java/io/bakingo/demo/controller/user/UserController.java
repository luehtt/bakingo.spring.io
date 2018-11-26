package io.bakingo.demo.controller.user;

import io.bakingo.demo.model.User;
import io.bakingo.demo.service.PrincipalService;
import io.bakingo.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user/account")
public class UserController {

    private UserService userService;
    private PrincipalService principalService;

    @Autowired
    public UserController(UserService userService, PrincipalService principalService) {
        this.userService = userService;
        this.principalService = principalService;
    }

    @GetMapping("")
    public ModelAndView index() {
        User user = principalService.getUser();
        user.setPassword("");
        user.setConfirmPassword("");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("user/account/index");
        return modelAndView;
    }

    @PostMapping("/update/password")
    public ModelAndView updatePassword(User user, BindingResult bindingResult, RedirectAttributes redirect) {
        try {
            user.setId(principalService.getUser().getId());
            User get = userService.updatePassword(user);
            if (!user.getPassword().equals(user.getConfirmPassword()))
                bindingResult.rejectValue("password", "", "*Nhập lại mật khẩu không đúng!!");
            ModelAndView modelAndView = new ModelAndView();
            redirect.addFlashAttribute("success", "true");
            redirect.addFlashAttribute("message", get.getName() + " is updated");
            modelAndView.setViewName("redirect:/user/account");
            return modelAndView;
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView();
            redirect.addFlashAttribute("success", "false");
            redirect.addFlashAttribute("message", e);
            modelAndView.setViewName("redirect:/user/account");
            return modelAndView;
        }
    }

    @PostMapping("/update/info")
    public ModelAndView updateInfo(User user, RedirectAttributes redirect) {
        try {
            user.setId(principalService.getUser().getId());
            User get = userService.updateInfo(user);
            ModelAndView modelAndView = new ModelAndView();
            redirect.addFlashAttribute("success", "true");
            redirect.addFlashAttribute("message", get.getName() + " is updated");
            modelAndView.setViewName("redirect:/user/account");
            return modelAndView;
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView();
            redirect.addFlashAttribute("success", "false");
            redirect.addFlashAttribute("message", e);
            modelAndView.setViewName("redirect:/user/account");
            return modelAndView;
        }
    }
}