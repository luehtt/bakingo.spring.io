package io.bakingo.demo.controller.user;

import io.bakingo.demo.model.Order;
import io.bakingo.demo.model.User;
import io.bakingo.demo.service.OrderService;
import io.bakingo.demo.service.PrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user/order")
public class OrderController {

    private final OrderService orderService;
    private final PrincipalService principalService;

    @Autowired
    public OrderController(OrderService orderService, PrincipalService principalService) {
        this.orderService = orderService;
        this.principalService = principalService;
    }

    @GetMapping({"", "/index"})
    public ModelAndView all() {
        User get = principalService.getUser();
        int id = get.getId();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("totalPending", orderService.findByStatus("Pending", id).size());
        modelAndView.addObject("totalShipping", orderService.findByStatus("Shipping", id).size());
        modelAndView.addObject("totalSucceeded", orderService.findByStatus("Succeeded", id).size());
        modelAndView.addObject("totalFailed", orderService.findByStatus("Failed", id).size());
        modelAndView.setViewName("user/order/index");
        return modelAndView;
    }

    @GetMapping({"/pending"})
    public ModelAndView pending() {
        User get = principalService.getUser();
        int id = get.getId();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("header", "Pending");
        modelAndView.addObject("items", orderService.findByStatus("Pending", id));
        modelAndView.setViewName("user/order/order");
        return modelAndView;
    }

    @GetMapping({"/shipping"})
    public ModelAndView shipping() {
        User get = principalService.getUser();
        int id = get.getId();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("header", "Shipping");
        modelAndView.addObject("items", orderService.findByStatus("Shipping", id));
        modelAndView.setViewName("user/order/order");
        return modelAndView;
    }

    @GetMapping({"/succeeded"})
    public ModelAndView succeeded() {
        User get = principalService.getUser();
        int id = get.getId();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("header", "Succeeded");
        modelAndView.addObject("items", orderService.findByStatus("Succeeded", id));
        modelAndView.setViewName("user/order/order");
        return modelAndView;
    }

    @GetMapping({"/failed"})
    public ModelAndView failed() {
        User get = principalService.getUser();
        int id = get.getId();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("header", "Failed");
        modelAndView.addObject("items", orderService.findByStatus("Failed", id));
        modelAndView.setViewName("user/order/order");
        return modelAndView;
    }

    @GetMapping({"/show/{id}"})
    public ModelAndView show(@PathVariable int id) {
        User user = principalService.getUser();
        Order order = orderService.findById(id, user);
        ModelAndView modelAndView = new ModelAndView();
        if (order.getUser().getId() != (int) user.getId()) modelAndView.setViewName("redirect:/order");
        else {
            modelAndView.addObject("user", user);
            modelAndView.addObject("item", order);
            modelAndView.setViewName("user/order/show");
        }
        return modelAndView;
    }
}
