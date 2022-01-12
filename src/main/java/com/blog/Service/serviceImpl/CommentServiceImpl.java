package com.blog.Service.serviceImpl;

import com.blog.Service.CommentService;
import com.blog.model.*;
import com.blog.pojo.CommentDto;
import com.blog.repository.repositoryImpl.CommentRepoImpl;
import com.blog.repository.repositoryImpl.LikeRepoImpl;
import com.blog.repository.repositoryImpl.PostRepoImpl;
import com.blog.repository.repositoryImpl.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepoImpl commentRepo;
    private final PostRepoImpl postRepo;
    private final UserRepoImpl userRepo;
    private final LikeRepoImpl likeRepo;

    @Autowired
    public CommentServiceImpl(CommentRepoImpl commentRepo, PostRepoImpl postRepo, UserRepoImpl userRepo, LikeRepoImpl likeRepo) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.likeRepo = likeRepo;
    }

    @Override
    public boolean createComment(Long userId, Long postId, Comment comment) {
        boolean result = false;
        Post post = postRepo.getPostById(postId);
        if(post != null){
            comment.setPostId(postId);
            comment.setUserId(userId);
            commentRepo.saveComment(comment);
            result = true;
        }
        return result;
    }

    @Override
    public List<CommentDto> getComments(long postId, long userId, User currentUser) {
        List<CommentDto> comments = new ArrayList<>();
        List<Comment> commentsData = commentRepo.findAllCommentByPostId(postId);
        System.out.println(commentsData);
        for (Comment commentEach:commentsData) {
            CommentDto comment = new CommentDto();
            comment.setUser(userRepo.getById(commentEach.getUserId()));
            comment.setPost(postRepo.getPostById(commentEach.getPostId()));
            comment.setId(commentEach.getId());
            comment.setPostId(commentEach.getPostId());
            comment.setComment(commentEach.getComment());
            comment.setUsername(comment.getUser().getLastName()+" "+comment.getUser().getFirstName());
            comment.setTitle(comment.getPost().getTitle());
            comment.setUserId(commentEach.getUserId());

            //the total number of likes on this particular post
            List<LikeComment> numberOfLikes = likeRepo.findAllByCommentId(commentEach.getId());
            int likeCount = numberOfLikes.size();
            comment.setNoLikes(likeCount);

            //return true if current user liked this post, else false
            List<LikePost> postLiked = likeRepo.findAllByPostPostIdAndUserId(commentEach.getId(), currentUser.getId());
            if(postLiked.size() > 0){
                comment.setLikedComment(true);
            }

            comments.add(comment);
        }
        return comments;
    }

    @Override
    public Comment editComment(Long commentId, User user, Long postId, Comment comment) {
        Comment data = commentRepo.findCommentById(commentId);
        data.setComment(comment.getComment());
        data.setUserId(user.getId());
        data.setPostId(postId);
        return commentRepo.updatePost(data);
    }

    @Override
    public String deleteComment(Long commentId) {
        return commentRepo.deleteCommentById(commentId);
    }
}
