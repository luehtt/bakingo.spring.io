package io.bakingo.demo.service;

import io.bakingo.demo.model.Comment;
import io.bakingo.demo.model.Item;
import io.bakingo.demo.model.User;
import io.bakingo.demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("commentService")
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public List<Comment> findByUserItem(User user, Item item) {
        int userId = user.getId();
        int itemId = item.getId();

        return commentRepository.findAll().stream()
                .filter(x -> x.getItem().getId() == itemId && x.getUser().getId() == userId)
                .collect(Collectors.toList());
    }

    public List<Comment> findByItemId(Integer id) {
        return commentRepository.findAll().stream().filter(x -> (int) x.getItem().getId() == id).collect(Collectors.toList());
    }

    public List<Comment> findByUserId(Integer id) {
        return commentRepository.findAll().stream().filter(x -> (int) x.getUser().getId() == id).collect(Collectors.toList());
    }

    public Comment findById(Integer id) {
        return commentRepository.findById(id).get();
    }

    public Comment findById(Integer id, User user) {
        Comment item = commentRepository.findById(id).get();
        if ((int) item.getUser().getId() != user.getId()) throw new AccessDeniedException("Denied access!! Hacker detected!?");
        return item;
    }

    public Comment save(Comment comment) {
        Timestamp timestamp = new Timestamp((new Date().getTime()));
        comment.setCreated(timestamp);
        comment.setUpdated(timestamp);
        commentRepository.save(comment);
        return comment;
    }

    public Comment delete(Integer id) {
        Comment get = findById(id);
        commentRepository.delete(get);
        commentRepository.flush();
        return get;
    }
}
