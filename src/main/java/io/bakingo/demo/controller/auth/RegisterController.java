package io.bakingo.demo.controller.auth;

import io.bakingo.demo.model.User;
import io.bakingo.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class RegisterController {

    private UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView register() {
        User user = new User();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("auth/register");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView save(@Valid User user, BindingResult bindingResult, RedirectAttributes redirect) {
        try {
            User get = userService.findByEmail(user.getEmail());
            ModelAndView modelAndView = new ModelAndView();
            if (get != null) bindingResult.rejectValue("email", "", "*Email đã được đăng kí!!");
            if (!user.getPassword().equals(user.getConfirmPassword()))
                bindingResult.rejectValue("password", "", "*Nhập lại mật khẩu không đúng!!");
            if (bindingResult.hasErrors()) modelAndView.setViewName("auth/register");
            else {
                userService.save(user);
                modelAndView.addObject("success", true);
                modelAndView.addObject("message", "Tài khoản " + user.getName() + " đã được tạo thành công");
                modelAndView.setViewName("auth/register");
            }
            return modelAndView;
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView();
            redirect.addFlashAttribute("success", "false");
            redirect.addFlashAttribute("message", e);
            modelAndView.setViewName("redirect:auth/register");
            return modelAndView;
        }
    }

}