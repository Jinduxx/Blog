package com.blog.Service.serviceImpl;

import com.blog.Service.LikeService;
import com.blog.model.Comment;
import com.blog.model.LikeComment;
import com.blog.model.LikePost;
import com.blog.model.Post;
import com.blog.repository.repositoryImpl.CommentRepoImpl;
import com.blog.repository.repositoryImpl.LikeRepoImpl;
import com.blog.repository.repositoryImpl.PostRepoImpl;
import com.blog.repository.repositoryImpl.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {


    private final LikeRepoImpl likesRepo;
    private final PostRepoImpl postRepo;
    private final UserRepoImpl userRepo;
    private final CommentRepoImpl commentRepo;

    @Autowired
    public LikeServiceImpl(LikeRepoImpl likesRepo, PostRepoImpl postRepo, UserRepoImpl userRepo, CommentRepoImpl commentRepo) {
        this.likesRepo = likesRepo;
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.commentRepo = commentRepo;
    }


    public boolean likePost(long userId, long postId, int action){
        boolean result = false;

        Post post = postRepo.getPostById(postId);
        if(post != null){
            LikePost like = new LikePost();
            like.setUser(userRepo.getById(userId));
            like.setPost(post);
            System.out.println("action in service " + action);
            if(action == 1){
                likesRepo.saveLikePost(like);
                System.out.println("saved");
            }else{
                likesRepo.deleteLikesByPostIdAndUserId(like.getUser().getId(), postId);
                System.out.println("deleted");
            }
            result = true;
        }

        return result;
    }

    public boolean likeComment(long userId, long commentId, int action){
        boolean result = false;

        Comment comment = commentRepo.findCommentById(commentId);
        if(comment != null){
            LikeComment like = new LikeComment();
            like.setUser(userRepo.getById(userId));
            like.setComment(comment);
            System.out.println("action in service " + action);
            if(action == 1){
                likesRepo.saveLikeComment(like);
                System.out.println("saved");
            }else{
                likesRepo.deleteLikesByPostIdAndUserId(like.getUser().getId(), commentId);
                System.out.println("deleted");
            }
            result = true;
        }

        return result;
    }
}
