package com.blog.Service;

import com.blog.model.Post;
import com.blog.model.User;
import com.blog.pojo.FavouritePostDto;
import com.blog.pojo.PostDto;

import java.util.List;

public interface PostService {

    boolean createPost(Post post, long userId);
    boolean createFavouritePost(FavouritePostDto favouritePostDto);
    Post getPostByIdAndUserId(long id,long userId);
    List<Post> getAllPostByUserId(long userId);
    List<PostDto> getAllFavouritePost(User currentUser);
    List<PostDto> getAllFriendsPost(long userId, User currentUser);
    Post getPostById(long postId);
    List<PostDto> getAllPost(User currentUser);
    Post updatePost(Post post);
    String deletePost(long id);
}
