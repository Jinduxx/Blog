package com.blog.controller;

import com.blog.Service.serviceImpl.LikeServiceImpl;
import com.blog.Service.serviceImpl.UserServiceImpl;
import com.blog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class LikeController {

    private final LikeServiceImpl likeService;

    @Autowired
    public LikeController(LikeServiceImpl likeService) {
        this.likeService = likeService;
    }


    @PostMapping("/likePost/{postId}/{action}")
    public String likePost(@PathVariable("postId") long postId, @PathVariable("action") int action, HttpSession session) {

        User user = (User) session.getAttribute("user");
        if(user == null) return "redirect:/";
        if(likeService.likePost(user.getId(), postId, action)){
            return "successful";
        }else{
           return "server error";
        }
    }

    @PostMapping("/likeComment/{commentId}/{action}")
    public String likeComments(@PathVariable("commentId") long commentId, @PathVariable("action") int action, HttpSession session) {

        User user = (User) session.getAttribute("user");
        if(user == null) return "redirect:/";
        if(likeService.likeComment(user.getId(), commentId, action)){
            return "successful";
        }else{
            return "server error";
        }
    }
}
