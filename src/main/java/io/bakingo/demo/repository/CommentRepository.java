package io.bakingo.demo.repository;

import io.bakingo.demo.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("commentRepository")
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
