package com.blog.Service.serviceImpl;

import com.blog.Service.PostService;
import com.blog.model.Comment;
import com.blog.model.LikePost;
import com.blog.model.Post;
import com.blog.model.User;
import com.blog.pojo.FavouritePostDto;
import com.blog.pojo.FriendDto;
import com.blog.pojo.PostDto;
import com.blog.repository.repositoryImpl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepoImpl postRepo;
    private final UserRepoImpl userRepo;
    private final LikeRepoImpl likeRepo;
    private final CommentRepoImpl commentRepo;
    private final FriendsRepoImpl friendsRepo;

    @Autowired
    public PostServiceImpl(PostRepoImpl postRepo, UserRepoImpl userRepo, LikeRepoImpl likeRepo, CommentRepoImpl commentRepo, FriendsRepoImpl friendsRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.likeRepo = likeRepo;
        this.commentRepo = commentRepo;
        this.friendsRepo = friendsRepo;
    }

    @Override
    public boolean createPost(Post post, long userId) {
        boolean result = false;
        User user = userRepo.getById(userId);
        if(user != null){
            post.setUser(user);
            postRepo.savePost(post);
            result = true;
        }
        return result;
    }

    @Override
    public boolean createFavouritePost(FavouritePostDto favouritePostDto) {
        boolean result = false;
        User user = userRepo.getById(favouritePostDto.getUserId());
        if(user != null){
            postRepo.saveFavouritePost(favouritePostDto);
            result = true;
        }
        return result;
    }

    @Override
    public Post updatePost(Post post) {
        return postRepo.updatePost(post);
    }

    @Override
    public Post getPostByIdAndUserId(long id, long userId) {
        Post post = postRepo.getPostById(id);
        post.setUser(userRepo.getById(userId));
        System.out.println(post.getUser());
        post.setName(post.getUser().getFirstName() + " " + post.getUser().getLastName());
        return post;
    }

    @Override
    public Post getPostById(long postId) {
        return postRepo.getPostById(postId);
    }

    @Override
    public List<Post> getAllPostByUserId(long userId) {

        List<Post> postData = postRepo.getAllPostById(userId);
        List<Post> posts = new ArrayList<>();

        for (Post postEach:postData) {

            Post post = new Post();
            post.setUser(userRepo.getById(userId));
            System.out.println(post.getUser());
            post.setId(postEach.getId());
            post.setTitle(postEach.getTitle());
            post.setBody(postEach.getBody());
            post.setName(post.getUser().getFirstName()+ " "+ post.getUser().getLastName());

            posts.add(post);
        }
        Collections.reverse(posts);

        return posts;
    }

    @Override
    public List<PostDto> getAllPostByTitle(String title, long userId) {

        List<Post> postData = postRepo.getPostByTitle(title);
        List<PostDto> posts = new ArrayList<>();

        for (Post postEach:postData) {

            PostDto post = new PostDto();
            post.setUser(userRepo.getById(userId));
            System.out.println(post.getUser());
            post.setId(postEach.getId());
            post.setTitle(postEach.getTitle());
            post.setBody(postEach.getBody());
            post.setName(post.getUser().getFirstName()+ " "+ post.getUser().getLastName());
            post.setUserId(postEach.getUserId());

            posts.add(post);
        }
        Collections.reverse(posts);

        return posts;
    }

    @Override
    public List<PostDto> getAllPost(User currentUser) {

        List<Post> postData = postRepo.getAllPost();
        List<PostDto> posts1 = new ArrayList<>();

        for (Post post1:postData) {
            PostDto post = new PostDto();

            post.setUser(userRepo.getById(post1.getUserId()));
            post.setId(post1.getId());
            post.setTitle(post1.getTitle());
            post.setBody(post1.getBody());
            post.setUserId(post1.getUserId());
            post.setName(post.getUser().getFirstName()+ " "+ post.getUser().getLastName());

            //the total number of likes on this particular post
            List<LikePost> numberOfLikes = likeRepo.findAllByPostPostId(post1.getId());
            int likeCount = numberOfLikes.size();
            post.setNoLikes(likeCount);

//            //the total number of comments on this particular post
            List<Comment> noOfComment = commentRepo.findAllCommentByPostId(post1.getId());
            int commentCount = noOfComment.size();
            post.setNoComments(commentCount);
//
//            //return true if current user liked this post, else false
            List<LikePost> postLiked = likeRepo.findAllByPostPostIdAndUserId(post1.getId(), currentUser.getId());
            if(postLiked.size() > 0){
                post.setLikedPost(true);
            }

            posts1.add(post);
        }
        Collections.reverse(posts1);

        return posts1;
    }

    @Override
    public List<PostDto> getAllFriendsPost(long userId, User currentUser) {

        List<PostDto> posts1 = new ArrayList<>();
        Set<Long> friendsId = new HashSet<>();
        List<FriendDto> friends = friendsRepo.getByUserId(userId);;
        for (FriendDto friend : friends) {
            friendsId.add(friend.getFriendsId());
        }
        for(Long e: friendsId){
            List<Post> postData = postRepo.getAllPostById(e);

            for (Post post1 : postData) {
                PostDto post = new PostDto();

                post.setUser(userRepo.getById(post1.getUserId()));
                post.setId(post1.getId());
                post.setTitle(post1.getTitle());
                post.setBody(post1.getBody());
                post.setName(post.getUser().getFirstName() + " " + post.getUser().getLastName());

                //the total number of likes on this particular post
                List<LikePost> numberOfLikes = likeRepo.findAllByPostPostId(post1.getId());
                int likeCount = numberOfLikes.size();
                post.setNoLikes(likeCount);

//            //the total number of comments on this particular post
                List<Comment> noOfComment = commentRepo.findAllCommentByPostId(post1.getId());
                int commentCount = noOfComment.size();
                post.setNoComments(commentCount);
//
//            //return true if current user liked this post, else false
                List<LikePost> postLiked = likeRepo.findAllByPostPostIdAndUserId(post1.getId(), currentUser.getId());
                if (postLiked.size() > 0) {
                    post.setLikedPost(true);
                }

                posts1.add(post);
            }
            Collections.reverse(posts1);


        }
        return posts1;
    }

    @Override
    public List<PostDto> getAllFavouritePost(User currentUser) {

        List<FavouritePostDto> FavouritePostData = postRepo.getAllFavouritePostByUserId(currentUser.getId());
        List<PostDto> posts1 = new ArrayList<>();
        List<Post> postData = new ArrayList<>();
        Post ps = new Post();
        for(FavouritePostDto pos: FavouritePostData){
            ps = postRepo.getPostById(pos.getPostId());
            postData.add(ps);
        }

        for (Post post1:postData) {
            PostDto post = new PostDto();

            post.setUser(userRepo.getById(post1.getUserId()));
            post.setId(post1.getId());
            post.setTitle(post1.getTitle());
            post.setBody(post1.getBody());
            post.setName(post.getUser().getFirstName()+ " "+ post.getUser().getLastName());

            //the total number of likes on this particular post
            List<LikePost> numberOfLikes = likeRepo.findAllByPostPostId(post1.getId());
            int likeCount = numberOfLikes.size();
            post.setNoLikes(likeCount);

//            //the total number of comments on this particular post
            List<Comment> noOfComment = commentRepo.findAllCommentByPostId(post1.getId());
            int commentCount = noOfComment.size();
            post.setNoComments(commentCount);
//
//            //return true if current user liked this post, else false
            List<LikePost> postLiked = likeRepo.findAllByPostPostIdAndUserId(post1.getId(), currentUser.getId());
            if(postLiked.size() > 0){
                post.setLikedPost(true);
            }
            posts1.add(post);
        }
        Collections.reverse(posts1);

        return posts1;
    }

    @Override
    public String deletePost(long id) {
        Post post  = postRepo.getPostById(id);
        String deletedPost = null;
        if(post != null){
            deletedPost = postRepo.deleteById(id);
        }
        return deletedPost;
    }
}
