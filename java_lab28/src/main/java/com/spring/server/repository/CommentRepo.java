package com.spring.server.repository;

import com.spring.server.entity.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepo extends CrudRepository<Comment, Long> {
    List<Comment> findAll();
    List<Comment> findCommentsByEventId(Long eventId);
    List<Comment> findCommentsByEvent_IdAndUser_Id(Long eventId, Long userId);
    Optional<Comment> findCommentByEventIdAndId(Long eventId, Long commentId);
    void deleteByEvent_IdAndId(Long eventId, Long commentId);
}
