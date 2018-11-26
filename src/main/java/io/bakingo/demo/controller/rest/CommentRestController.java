package io.bakingo.demo.controller.rest;

import io.bakingo.demo.model.Comment;
import io.bakingo.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentRestController {

    private CommentService commentService;

    @Autowired
    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comment/item/{id}")
    public List<Comment> get(@PathVariable(value = "id") Integer id){
        return commentService.findByItemId(id);
    }
}
