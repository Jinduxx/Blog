package com.blog.repository;

import com.blog.model.LikeComment;
import com.blog.model.LikePost;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LikeRepo {

    void saveLikePost(LikePost like);
    void saveLikeComment(LikeComment comment);
    List<LikePost> findAllByPostPostId(Long postId);
    List<LikeComment> findAllByCommentId(Long commentId);
    List<LikePost> findAllByPostPostIdAndUserId(Long postId, Long personId);
    List<LikeComment> findAllByCommentIdAndUserId(Long userId, Long commentId);
    @Transactional
    String deleteLikesByPostIdAndUserId(long userId, long postId);
    String deleteCommentLikesByPostIdAndUserId(long userId, long postId);
}
