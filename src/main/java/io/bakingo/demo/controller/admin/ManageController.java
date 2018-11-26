package io.bakingo.demo.controller.admin;

import io.bakingo.demo.model.Order;
import io.bakingo.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/order")
public class ManageController {

    private final OrderService orderService;

    @Autowired
    public ManageController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping({"", "/pending"})
    public ModelAndView pending() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("items", orderService.findByStatus("Pending"));
        modelAndView.addObject("header", "Pending");
        modelAndView.setViewName("admin/order/order");
        return modelAndView;
    }

    @GetMapping("/shipping")
    public ModelAndView shipping() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("items", orderService.findByStatus("Shipping"));
        modelAndView.addObject("header", "Shipping");
        modelAndView.setViewName("admin/order/order");
        return modelAndView;
    }

    @GetMapping("/succeeded")
    public ModelAndView succeeded() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("items", orderService.findByStatus("Succeeded"));
        modelAndView.addObject("header", "Succeeded");
        modelAndView.setViewName("admin/order/order");
        return modelAndView;
    }

    @GetMapping("/failed")
    public ModelAndView failed() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("items", orderService.findByStatus("Failed"));
        modelAndView.addObject("header", "Failed");
        modelAndView.setViewName("admin/order/order");
        return modelAndView;
    }

    @GetMapping("/show/{id}")
    public ModelAndView show(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Order item = orderService.findById(id);
            modelAndView.addObject("item", item);
            modelAndView.setViewName("admin/order/show");
            return modelAndView;
        } catch(Exception e) {
            modelAndView.setViewName("redirect:/admin/order");
        }
        return modelAndView;
    }

    @PostMapping("/shipping/{id}")
    public String toShipping(@PathVariable int id, RedirectAttributes redirect) {
        try {
            Order item = orderService.updateStatus(id, "Shipping");
            redirect.addFlashAttribute("success", "true");
            redirect.addFlashAttribute("message", "Order No." + item.getId() + " is updated");
            return "redirect:/admin/order";
        } catch(Exception e) {
            redirect.addFlashAttribute("success", "false");
            redirect.addFlashAttribute("message", e);
            return "redirect:/admin/order";
        }
    }

    @PostMapping("/succeeded/{id}")
    public String toSucceeded(@PathVariable int id, RedirectAttributes redirect) {
        try {
            Order item = orderService.updateStatus(id, "Succeeded");
            redirect.addFlashAttribute("success", "true");
            redirect.addFlashAttribute("message", "Order No." + item.getId() + " is updated");
            return "redirect:/admin/order";
        } catch(Exception e) {
            redirect.addFlashAttribute("success", "false");
            redirect.addFlashAttribute("message", e);
            return "redirect:/admin/order";
        }
    }

    @PostMapping("/failed/{id}")
    public String toFailed(@PathVariable int id, RedirectAttributes redirect) {
        try {
            Order item = orderService.updateStatus(id, "Failed");
            redirect.addFlashAttribute("success", "true");
            redirect.addFlashAttribute("message", "Order No." + item.getId() + " is updated");
            return "redirect:/admin/order";
        } catch(Exception e) {
            redirect.addFlashAttribute("success", "false");
            redirect.addFlashAttribute("message", e);
            return "redirect:/admin/order";
        }
    }
}
