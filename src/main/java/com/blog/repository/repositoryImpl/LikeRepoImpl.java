package com.blog.repository.repositoryImpl;

import com.blog.model.LikeComment;
import com.blog.model.LikePost;
import com.blog.repository.LikeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LikeRepoImpl implements LikeRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LikeRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String INSERT_LIKE_QUERY = "INSERT INTO LIKES(user_id,post_id) values(?,?)";
    private static final String INSERT_LIKE_COMMENT_QUERY = "INSERT INTO LIKES_COMMENT(user_id,comment_id) values(?,?)";
    private static final String GET_LIKE_BY_POSTID_QUERY = "SELECT * FROM LIKES WHERE POST_ID=?";
    private static final String GET_LIKE_COMMENT_BY_POSTID_QUERY = "SELECT * FROM LIKES_COMMENT WHERE ID=?";
    private static final String GET_LIKE_BY_POSTID_AND_USERID_QUERY = "SELECT * FROM LIKES WHERE USER_ID=? AND POST_ID=?";
    private static final String GET_LIKE_COMMENT_BY_POST_ID_AND_USER_ID_QUERY = "SELECT * FROM LIKES_COMMENT WHERE USER_ID=? AND POST_ID=?";
    private static final String DELETE_LIKE_BY_USERID_AND_POSTID = "DELETE FROM LIKES WHERE USER_ID=? AND POST_ID=?";
    private static final String DELETE_LIKE_COMMENT_BY_USERID_AND_COMMENTID = "DELETE FROM LIKES_COMMENT WHERE USER_ID=? AND COMMENT_ID=?";


    @Override
    public void saveLikePost(LikePost like) {
        jdbcTemplate.update(INSERT_LIKE_QUERY, like.getUser().getId(), like.getPost().getId());
    }

    @Override
    public void saveLikeComment(LikeComment comment) {
        jdbcTemplate.update(INSERT_LIKE_COMMENT_QUERY, comment.getUser().getId(), comment.getComment().getId());
    }

    @Override
    public List<LikePost> findAllByPostPostId(Long postId) {
            return  jdbcTemplate.query(GET_LIKE_BY_POSTID_QUERY,
                    BeanPropertyRowMapper.newInstance(LikePost.class),postId);
    }

    @Override
    public List<LikeComment> findAllByCommentId(Long commentId) {
            return  jdbcTemplate.query(GET_LIKE_COMMENT_BY_POSTID_QUERY,
                    BeanPropertyRowMapper.newInstance(LikeComment.class),commentId);
    }

    @Override
    public List<LikePost> findAllByPostPostIdAndUserId(Long userId, Long postId) {
            return  jdbcTemplate.query(GET_LIKE_BY_POSTID_AND_USERID_QUERY,
                    BeanPropertyRowMapper.newInstance(LikePost.class), userId,postId);
    }

    @Override
    public List<LikeComment> findAllByCommentIdAndUserId(Long userId, Long commentId) {
            return  jdbcTemplate.query(GET_LIKE_COMMENT_BY_POST_ID_AND_USER_ID_QUERY,
                    BeanPropertyRowMapper.newInstance(LikeComment.class), userId,commentId);
    }

    @Override
    public String deleteLikesByPostIdAndUserId(long userId, long postId) {
        jdbcTemplate.update(DELETE_LIKE_BY_USERID_AND_POSTID, userId, postId);
        return "Likes got deleted";
    }

    @Override
    public String deleteCommentLikesByPostIdAndUserId(long userId, long commentId) {
        jdbcTemplate.update(DELETE_LIKE_COMMENT_BY_USERID_AND_COMMENTID, userId, commentId);
        return "Comment Likes got deleted";
    }
}
