package io.bakingo.demo.controller.admin;

import io.bakingo.demo.model.User;
import io.bakingo.demo.service.OrderService;
import io.bakingo.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/customer")
public class CustomerController {

    private final UserService customerService;
    private final OrderService orderService;

    @Autowired
    public CustomerController(UserService customerService, OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @GetMapping(value={"", "/index"})
    public ModelAndView all(@RequestParam(value = "search", required = false) String search) {
        List<User> items = customerService.findCustomer(true);
        if (search != null && !search.equals("")) {
            final String filter = search.toLowerCase();
            items = items.stream()
                    .filter(x -> x.getName().toLowerCase().contains(filter) || x.getEmail().toLowerCase().contains(filter))
                    .collect(Collectors.toList());
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("items", items);
        modelAndView.addObject("enabled", true);
        modelAndView.setViewName("admin/customer/index");
        return modelAndView;
    }

    @GetMapping("/disabled")
    public ModelAndView disabled(@RequestParam(value = "search", required = false) String search) {
        List<User> items = customerService.findCustomer(false);
        if (search != null && !search.equals("")) {
            final String filter = search.toLowerCase();
            items = items.stream()
                    .filter(x -> x.getName().toLowerCase().contains(filter) || x.getEmail().toLowerCase().contains(filter))
                    .collect(Collectors.toList());
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("items", items);
        modelAndView.addObject("enabled", false);
        modelAndView.setViewName("admin/customer/index");
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable int id) {
        User user = customerService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        modelAndView.addObject("orders", orderService.findByUser(user));
        modelAndView.setViewName("admin/customer/edit");
        return modelAndView;
    }

    @PostMapping("/{id}/enabled")
    public String enabled(@PathVariable int id, RedirectAttributes redirect) {
        try {
            User user = customerService.update(id, true);
            redirect.addFlashAttribute("success", "true");
            redirect.addFlashAttribute("message", user.getName() + " is enabled");
            return "redirect:/admin/customer/disabled";
        } catch(Exception e) {
            redirect.addFlashAttribute("success", "false");
            redirect.addFlashAttribute("message", e);
            return "redirect:/admin/customer/disabled";
        }
    }

    @PostMapping("/{id}/disabled")
    public String disabled(@PathVariable int id, RedirectAttributes redirect) {
        try {
            User user = customerService.update(id, false);
            redirect.addFlashAttribute("success", "true");
            redirect.addFlashAttribute("message", user.getName() + " is enabled");
            return "redirect:/admin/customer/";
        } catch(Exception e) {
            redirect.addFlashAttribute("success", "false");
            redirect.addFlashAttribute("message", e);
            return "redirect:/admin/customer/";
        }
    }
}
