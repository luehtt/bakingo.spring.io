package io.bakingo.demo.controller.user;

import io.bakingo.demo.model.Comment;
import io.bakingo.demo.model.Item;
import io.bakingo.demo.model.User;
import io.bakingo.demo.service.CommentService;
import io.bakingo.demo.service.ItemService;
import io.bakingo.demo.service.PrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user/comment")
public class CommentController {

    private final ItemService itemService;
    private final CommentService commentService;
    private final PrincipalService principalService;

    @Autowired
    public CommentController(ItemService itemService, CommentService commentService, PrincipalService principalService) {
        this.itemService = itemService;
        this.commentService = commentService;
        this.principalService = principalService;
    }

    @GetMapping({"", "/index"})
    public ModelAndView all() {
        User user = principalService.getUser();
        List<Comment> comments = commentService.findByUserId(user.getId());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("items", comments);
        modelAndView.setViewName("user/comment/index");
        return modelAndView;
    }

    @GetMapping("/{itemId}/post")
    public ModelAndView post(@PathVariable int itemId) {
        User user = principalService.getUser();
        Item item = itemService.findById(itemId);
        List<Comment> comments = commentService.findByUserItem(user, item);
        Comment comment = new Comment();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("comment", comment);
        modelAndView.addObject("item", item);
        modelAndView.addObject("comments", comments);
        modelAndView.setViewName("user/comment/post");
        return modelAndView;
    }

    @PostMapping("/{itemId}/post")
    public String save(@ModelAttribute("comment") Comment comment, @PathVariable int itemId, RedirectAttributes redirect) {
        try {
            User user = principalService.getUser();
            Item item = itemService.findById(itemId);
            comment.setUser(user);
            comment.setItem(item);
            // protected delete from unauthorized access
            if (comment.getUser().getId() != (int) principalService.getUser().getId()) return "redirect:/user/comment";
            if (comment.getItem().getId() != itemId) return "redirect:/user/comment";

            Comment get = commentService.save(comment);
            redirect.addFlashAttribute("success", "true");
            redirect.addFlashAttribute("message", "Comment No. " + get.getId() + " is created");
            return "redirect:/user/comment/" + itemId + "/post";
        } catch (Exception e) {
            redirect.addFlashAttribute("success", "false");
            redirect.addFlashAttribute("message", e);
            return "redirect:/user/comment/";
        }
    }

    @DeleteMapping("/{itemId}/delete/{id}")
    public String destroy(@PathVariable("itemId") int itemId, @PathVariable("id") int id, RedirectAttributes redirect) {
        try {
            // protected delete from unauthorized access
            Comment comment = commentService.findById(id);
            if (comment.getUser().getId() != (int) principalService.getUser().getId()) return "redirect:/user/comment";
            if (comment.getItem().getId() != itemId) return "redirect:/user/comment";

            Comment get = commentService.delete(id);
            redirect.addFlashAttribute("success", "true");
            redirect.addFlashAttribute("message", "Comment No. " + get.getId() + " is deleted");
            return "redirect:/user/comment/" + itemId + "/post";
        } catch(Exception e) {
            redirect.addFlashAttribute("success", "false");
            redirect.addFlashAttribute("message", e);
            return "redirect:/user/comment/";
        }
    }
}
