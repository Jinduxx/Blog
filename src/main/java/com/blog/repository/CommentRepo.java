package com.blog.repository;

import com.blog.model.Comment;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepo {

    void saveComment(Comment comment);
    Comment updatePost(Comment comment);
    List<Comment> findAllCommentByPostId(Long postId);
    List<Comment> findCommentByComment(String comment);
    Comment findCommentById(Long commentId);
    @Transactional
    String deleteCommentById(Long id);
}
