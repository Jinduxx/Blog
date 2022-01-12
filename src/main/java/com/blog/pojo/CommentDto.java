package com.blog.pojo;

import com.blog.model.Comment;
import com.blog.model.LikePost;
import com.blog.model.Post;
import com.blog.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long postId;
    private Long id;
    private String username;
    private String comment;
    private String title;
    private Long userId;
    private User user;
    private Post post;
    private int noLikes;
    private int noComments;
    private boolean likedComment;
    private List<Comment> comments;
    private LikePost myLike;

}
