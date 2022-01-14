package com.blog.Service;

import com.blog.model.Comment;
import com.blog.model.User;
import com.blog.pojo.CommentDto;

import java.util.List;

public interface CommentService {

    boolean createComment(Long userId, Long postId, Comment comment);
    List<CommentDto> getComments(long postId, long userId, User currentUser);
    List<CommentDto> searchComments(long postId, long userId, String comm, User currentUser);
    Comment editComment(Long commentId, User user, Long postId, Comment comment);
    String deleteComment(Long commentId);
}
