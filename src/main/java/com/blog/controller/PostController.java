package com.blog.controller;

import com.blog.Service.serviceImpl.PostServiceImpl;
import com.blog.model.Post;
import com.blog.model.User;
import com.blog.pojo.FavouritePostDto;
import com.blog.pojo.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class PostController {

    private final PostServiceImpl postService;

    @Autowired
    public PostController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @PostMapping("/post")
    public String makePost(@RequestBody Post post, HttpSession session) {

        User user = (User) session.getAttribute("user");

        if(user == null){
            return "redirect:/";
        }
        //set person
        post.setUser(user);

        if(postService.createPost(post,user.getId())) {
            session.setAttribute("message", "Post uploading successfully");
            return "successful";
        }
        session.setAttribute("message", "Error uploading post to database");
        return "failed";
//        return "redirect:/home";
    }

    @PostMapping("/post/{postId}/{postUserId}")
    public String addFavouritePost(@PathVariable("postId") long postId, @PathVariable("postUserId") long postUserId, HttpSession session) {

        User user = (User) session.getAttribute("user");
        FavouritePostDto favouritePostDto = new FavouritePostDto();
        favouritePostDto.setPostId(postId);
        favouritePostDto.setPostUserId(postUserId);
        favouritePostDto.setUserId(user.getId());
        if(user == null){
            return "redirect:/";
        }

        if(postService.createFavouritePost(favouritePostDto)) {
            session.setAttribute("message", "Post uploading successfully");
            return "successful";
        }
        session.setAttribute("message", "Error uploading post to database");
        return "failed";
//        return "redirect:/home";
    }

    @PutMapping("/post")
    public String updatePost(@RequestBody Post post, HttpSession session) {

//        return postService.updatePost(post);
        User user = (User) session.getAttribute("user");
        Post post1 = postService.getPostById(post.getId());
        if(post1.getUserId() == user.getId()){
            post.setUser(user);
            if(user == null) return "redirect:/";

            if(postService.updatePost(post) != null) {
                session.setAttribute("message", "Post edited successfully");
                return "Successful";
            }else{
                session.setAttribute("message", "Error editing post!");
                return "failed";
            }
        }

        return "You can't change this post";

//        return "redirect:/home";
    }

    @GetMapping("/allPost")
    public Object showAllPost(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        List<PostDto> post = postService.getAllPost(user);

        model.addAttribute("postData", post);
        model.addAttribute("user", user);
        return post;
    }

    @GetMapping("/searchedPost")
    public Object showAllSearchedPost(@RequestBody Post posts, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        List<PostDto> post = postService.getAllPostByTitle(posts.getTitle(), user.getId());

        model.addAttribute("postData", post);
        model.addAttribute("user", user);
        return post;
    }

    @GetMapping("/friendsPosts")
    public Object showAllFriendsPost(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        List<PostDto> post = postService.getAllFriendsPost(user.getId(), user);

        model.addAttribute("postData", post);
        model.addAttribute("user", user);
        return post;
    }

    @GetMapping("/favouritePost")
    public Object showAllFavouritePost(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        List<PostDto> post = postService.getAllFavouritePost(user);

        model.addAttribute("favouritePostData", post);
        model.addAttribute("user", user);
        return post;
    }

//    @GetMapping("/post/{id}")
//    public Post getPost(@PathVariable("id") long id, HttpSession session) {
//        User user = (User) session.getAttribute("user");
//        return postService.getPostByIdAndUserId(id, user.getId());
//    }
//
//    @GetMapping("/allPost/{userId}")
//    public List<Post> showAllPostByUserId(@PathVariable("userId") long userId) {
//        return postService.getAllPostByUserId(userId);
//    }

    @DeleteMapping("/post/{id}")
    public String deletePost(@PathVariable("id") long id){
        return postService.deletePost(id);
    }

}
