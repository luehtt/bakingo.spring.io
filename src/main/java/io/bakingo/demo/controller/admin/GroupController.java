package io.bakingo.demo.controller.admin;

import io.bakingo.demo.model.Group;
import io.bakingo.demo.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/group")
public class GroupController {

    private GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("")
    public ModelAndView all() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("items", groupService.findAll());
        modelAndView.setViewName("admin/group/index");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createGroup() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("item", new Group());
        modelAndView.setViewName("admin/group/create");
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("item") Group item, RedirectAttributes redirect) {
        try {
            groupService.save(item);
            redirect.addFlashAttribute("success", "true");
            redirect.addFlashAttribute("message", item.getName() + " is updated");
            return "redirect:/admin/group";
        } catch(Exception e) {
            redirect.addFlashAttribute("success", "false");
            redirect.addFlashAttribute("message", e);
            return "redirect:/admin/group";
        }
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView();
        Group item = groupService.findById(id);
        modelAndView.addObject("item", item);
        modelAndView.setViewName("admin/group/edit");
        return modelAndView;
    }

    @PutMapping("/{id}/edit")
    public String update(@ModelAttribute("item") Group item, @PathVariable int id, RedirectAttributes redirect) {
        try {
            groupService.update(item, id);
            redirect.addFlashAttribute("success", "true");
            redirect.addFlashAttribute("message", item.getName() + " is updated");
            return "redirect:/admin/group";
        } catch(Exception e) {
            redirect.addFlashAttribute("success", "false");
            redirect.addFlashAttribute("message", e);
            return "redirect:/admin/group";
        }
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView();
        Group item = groupService.findById(id);
        modelAndView.addObject("item", item);
        modelAndView.setViewName("admin/group/delete");
        return modelAndView;
    }

    @DeleteMapping("/{id}/delete")
    public String destroy(@PathVariable(value = "id") Integer id, RedirectAttributes redirect) {
        try {
            Group item = groupService.delete(id);
            redirect.addFlashAttribute("success", "true");
            redirect.addFlashAttribute("message", item.getName() + " is deleted");
            return "redirect:/admin/group";
        } catch(Exception e) {
            redirect.addFlashAttribute("success", "false");
            redirect.addFlashAttribute("message", e);
            return "redirect:/admin/group";
        }
    }
}
