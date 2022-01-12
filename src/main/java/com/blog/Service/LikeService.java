package com.blog.Service;

import com.blog.model.User;

public interface LikeService {
    boolean likePost(long userId, long postId, int action);
    public boolean likeComment(long userId, long commentId, int action);
}
