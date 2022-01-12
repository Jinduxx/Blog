package com.blog.repository;

import com.blog.model.Post;
import com.blog.model.User;

import java.util.List;

public interface PostRepo {

    void savePost(Post post);

    Post updatePost(Post post);

    Post getPostById(long postId);

    List<Post> getAllPostById (long userId);

    public void saveFavouritePost(Post post, User user);

    public List<Post> getAllFavouritePostByUserId(long userId);

    public List<Post> getAllPost();

    String deleteById(long id);
}
