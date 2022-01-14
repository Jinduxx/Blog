package com.blog.controller;

import com.blog.Service.serviceImpl.CommentServiceImpl;
import com.blog.model.Comment;
import com.blog.model.User;
import com.blog.pojo.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class CommentController {

    private final CommentServiceImpl commentService;

    @Autowired
    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @PostMapping( "/comment/{postId}")
    public String makeComment(@PathVariable("postId") long postId, @RequestBody Comment comment, HttpSession session) {

        User user = (User) session.getAttribute("user");
        if(user == null) return "redirect:/";
        if(commentService.createComment(user.getId(), postId, comment)){
            return "Comment made successfully";
        }else{
            return "Failed to comment";
        }
//        return "redirect:/home";
    }

    @GetMapping("/comment/{postId}")
    public List<CommentDto> showComment(@PathVariable("postId") long postId, HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");
        if(user == null) throw new NullPointerException("user is null");
//        return "redirect:/";
        List<CommentDto> commentList = commentService.getComments(postId, user.getId(), user);
        if (commentList.size() == 0){
            return null;
//            return "redirect:/home";
        }
        model.addAttribute("commentData", commentList);
        model.addAttribute("user", user);
        return commentList;
//      return "comment";
    }

    @GetMapping("/commentResult/{postId}")
    public List<CommentDto> showSearchedComment(@PathVariable("postId") long postId, @RequestBody Comment comment, HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");
        if(user == null) throw new NullPointerException("user is null");
//        return "redirect:/";
        List<CommentDto> commentList = commentService.searchComments(postId, user.getId(),comment.getComment(), user);
        if (commentList.size() == 0){
            return null;
//            return "redirect:/home";
        }
        model.addAttribute("commentData", commentList);
        model.addAttribute("user", user);
        return commentList;
//      return "comment";
    }

    @PutMapping( "/comment/{commentId}/{postId}")
    public String editComment(@PathVariable("commentId") long commentId, @PathVariable("postId") long postId,
                              @RequestBody Comment comment, HttpSession session) {

        System.out.println("in edit comment");
        User user = (User) session.getAttribute("user");
        if(user == null) throw  new NullPointerException("user is null");
//        return "redirect:/";
        if(commentService.editComment(commentId, user, postId, comment) != null){
            session.setAttribute("message", "Comment edited successfully");
        }else {
            session.setAttribute("message", "Failed to edit comment");
        }

        return "Successful";
//        return "redirect:/comment/"+postId;
    }

    @DeleteMapping( "/comment/{commentId}")
    public String deleteComment(@PathVariable("commentId") long commentId, HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) return "user is null";
        if (commentService.deleteComment(commentId).equals("Comments got deleted")) {
            return "successful";
        } else {
            return "failed";
        }
//        return "redirect:/";
    }
}
