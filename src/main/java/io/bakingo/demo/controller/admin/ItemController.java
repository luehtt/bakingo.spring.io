package io.bakingo.demo.controller.admin;

import io.bakingo.demo.model.Item;
import io.bakingo.demo.service.GroupService;
import io.bakingo.demo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/item")
public class ItemController {

    private final ItemService itemService;
    private final GroupService groupService;

    @Autowired
    public ItemController(ItemService itemService, GroupService groupService) {
        this.itemService = itemService;
        this.groupService = groupService;
    }

    @GetMapping({"", "/index"})
    public ModelAndView all(@RequestParam(value = "search", required = false) String search) {
        List<Item> items = itemService.findAll(true);
        if (search != null && !search.equals("")) {
            final String filter = search.toLowerCase();
            items = items.stream().filter(x -> x.getName().toLowerCase().contains(filter)).collect(Collectors.toList());
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("items", items);
        modelAndView.addObject("enabled", true);
        modelAndView.setViewName("admin/item/index");
        return modelAndView;
    }

    @GetMapping("/disabled")
    public ModelAndView disabled(@RequestParam(value = "search", required = false) String search) {
        List<Item> items = itemService.findAll(false);
        if (search != null && !search.equals("")) {
            final String filter = search.toLowerCase();
            items = items.stream().filter(x -> x.getName().toLowerCase().contains(filter)).collect(Collectors.toList());
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("items", items);
        modelAndView.addObject("enabled", false);
        modelAndView.setViewName("admin/item/index");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("item", new Item());
        modelAndView.addObject("groups", groupService.findAll());
        modelAndView.setViewName("admin/item/create");
        return modelAndView;
    }

    @PostMapping("/create")
    public String save(@ModelAttribute("item") Item item, RedirectAttributes redirect) {
        try {
            itemService.save(item);
            redirect.addFlashAttribute("success", "true");
            redirect.addFlashAttribute("message", item.getName() + " is created");
            return "redirect:/admin/item";
        } catch(Exception e) {
            redirect.addFlashAttribute("success", "false");
            redirect.addFlashAttribute("message", e);
            return "redirect:/admin/item";
        }
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Item item = itemService.findById(id);
            modelAndView.addObject("item", item);
            modelAndView.addObject("groups", groupService.findAll());
            modelAndView.setViewName("admin/item/edit");
        } catch(Exception e) {
            modelAndView.setViewName("redirect:/admin/item");
        }
        return modelAndView;
    }

    @PostMapping("/{id}/edit")
    public String update(@ModelAttribute("item") Item item, @PathVariable int id, RedirectAttributes redirect) {
        try {
            itemService.update(item, id);
            redirect.addFlashAttribute("success", "true");
            redirect.addFlashAttribute("message", item.getName() + " is updated");
            return "redirect:/admin/item";
        } catch(Exception e) {
            redirect.addFlashAttribute("success", "false");
            redirect.addFlashAttribute("message", e);
            return "redirect:/admin/item";
        }
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Item item = itemService.findById(id);
            modelAndView.addObject("item", item);
            modelAndView.setViewName("admin/item/delete");
        } catch(Exception e) {
            modelAndView.setViewName("redirect:/admin/item");
        }
        return modelAndView;
    }

    @DeleteMapping("/{id}/delete")
    public String destroy(@PathVariable(value = "id") Integer id, RedirectAttributes redirect) {
        try {
            Item item = itemService.delete(id);
            redirect.addFlashAttribute("success", "true");
            redirect.addFlashAttribute("message", item.getName() + " is deleted");
            return "redirect:/admin/item";
        } catch(Exception e) {
            redirect.addFlashAttribute("success", "false");
            redirect.addFlashAttribute("message", e);
            return "redirect:/admin/item";
        }
    }
}
