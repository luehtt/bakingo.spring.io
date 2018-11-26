package io.bakingo.demo.controller.admin;

import io.bakingo.demo.model.Comment;
import io.bakingo.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/feedback")
public class FeedbackController {

    private CommentService commentService;

    @Autowired
    public FeedbackController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("")
    public ModelAndView all() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("items", commentService.findAll());
        modelAndView.setViewName("admin/feedback/index");
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView edit(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView();
        Comment item = commentService.findById(id);
        modelAndView.addObject("item", item);
        modelAndView.setViewName("admin/feedback/show");
        return modelAndView;
    }

    @DeleteMapping("/{id}/delete")
    public String destroy(@PathVariable(value = "id") Integer id, RedirectAttributes redirect) {
        try {
            Comment item = commentService.delete(id);
            redirect.addFlashAttribute("success", "true");
            redirect.addFlashAttribute("message",  "Comment " + item.getId() + " is deleted");
            return "redirect:/admin/feedback";
        } catch(Exception e) {
            redirect.addFlashAttribute("success", "false");
            redirect.addFlashAttribute("message", e);
            return "redirect:/admin/feedback";
        }
    }
}
