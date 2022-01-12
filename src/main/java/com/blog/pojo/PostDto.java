package com.blog.pojo;

import com.blog.model.Comment;
import com.blog.model.LikePost;
import com.blog.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {


    private long id;
    private String title;
    private String body;
    private String name;
    private long userId;
    private int noLikes;
    private int noComments;
    private boolean likedPost;
    private User user;
    private List<Comment> comments;
    private LikePost myLike;
}
