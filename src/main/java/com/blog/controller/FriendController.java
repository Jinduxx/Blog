package com.blog.controller;

import com.blog.Service.serviceImpl.FriendServiceImpl;
import com.blog.Service.serviceImpl.UserServiceImpl;
import com.blog.model.User;
import com.blog.pojo.FriendDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Repository
public class FriendController {

    private final FriendServiceImpl friendService;
    private final UserServiceImpl userService;

    @Autowired
    public FriendController(FriendServiceImpl friendService, UserServiceImpl userService) {
        this.friendService = friendService;
        this.userService = userService;
    }

    @PostMapping("/friend")
    public ResponseEntity<?> makeFriend(@RequestBody User user1, HttpSession session) {

        User user = (User) session.getAttribute("user");
        User friend = userService.getUserByUsername(user1.getUserName());

        if(friend == null){
//            return "redirect:/";
            return ResponseEntity.ok().body("friend doesnt exist");
        }
        if(friendService.createFriend(friend.getId(), user.getId())){
            return ResponseEntity.ok().body("Successful");
        }
        return ResponseEntity.ok().body("failed");
//        return "redirect:/home";
    }

    @GetMapping("/friend")
    public Object getFriendByUserId(HttpSession session) {

        User user = (User) session.getAttribute("user");
        List<FriendDto> friend = friendService.getFriendByUserId(user.getId());
        if(friend.isEmpty()){
//            return "redirect:/";
            return null;
        }
        return friend;
//        return "redirect:/home";
    }

    @GetMapping("/friend/{friendId}")
    public Object getFriendByUserIdAndFriendId(@PathVariable long friendId, HttpSession session) {

        User user = (User) session.getAttribute("user");
        FriendDto friend = friendService.getFriendByUserIdAndFriendId(user.getId(),friendId);
        if(friend == null){
//            return "redirect:/";
            return null;
        }
        return friend;
//        return "redirect:/home";
    }

    @DeleteMapping("/friend/{friendId}")
    public String deletePost(@PathVariable("friendId") long friendId, HttpSession session){
        User user = (User) session.getAttribute("user");
        return friendService.deleteFriend(friendId, user.getId());
    }
}
