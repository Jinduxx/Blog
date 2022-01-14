package com.blog.repository;

import com.blog.model.Post;
import com.blog.pojo.FavouritePostDto;

import java.util.List;

public interface PostRepo {

    void savePost(Post post);

    Post updatePost(Post post);

    Post getPostById(long postId);

    List<Post> getAllPostById (long userId);

    List<Post> getPostByTitle(String title);

    void saveFavouritePost(FavouritePostDto favouritePostDto);

    List<FavouritePostDto> getAllFavouritePostByUserId(long userId);

    List<Post> getAllPost();

    String deleteById(long id);
}
