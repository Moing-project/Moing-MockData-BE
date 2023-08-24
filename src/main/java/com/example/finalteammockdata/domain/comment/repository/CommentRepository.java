package com.example.finalteammockdata.domain.comment.repository;

import com.example.finalteammockdata.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository {
    Optional<Comment> findByWorkIdAndCommentId(Long WorkId, Long commentId);
    List<Comment> findAllByWorkId(Long WorkId);
}
