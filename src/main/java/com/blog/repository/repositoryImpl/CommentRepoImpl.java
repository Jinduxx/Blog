package com.blog.repository.repositoryImpl;

import com.blog.model.Comment;
import com.blog.model.Post;
import com.blog.repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepoImpl implements CommentRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CommentRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String INSERT_COMMENT_QUERY = "INSERT INTO COMMENTS(comment,user_id,post_id) values(?,?,?)";
    private static final String UPDATE_COMMENT_BY_ID_QUERY = "UPDATE COMMENTS SET comment=? WHERE ID=?";
    private static final String GET_COMMENT_BY_POSTID_QUERY = "SELECT * FROM COMMENTS WHERE POST_ID=?";
    private static final String GET_COMMENT_BY_COMMENTS_QUERY = "SELECT * FROM COMMENTS WHERE COMMENT=?";
    private static final String GET_COMMENT_BY_ID_QUERY = "SELECT * FROM COMMENTS WHERE ID=?";
    private static final String DELETE_COMMENT_BY_ID = "DELETE FROM COMMENTS WHERE ID=?";

    @Override
    public void saveComment(Comment comment) {
        jdbcTemplate.update(INSERT_COMMENT_QUERY,comment.getComment(), comment.getUserId(), comment.getPostId());
    }

    @Override
    public List<Comment> findAllCommentByPostId(Long postId) {
        return  jdbcTemplate.query(GET_COMMENT_BY_POSTID_QUERY,
                BeanPropertyRowMapper.newInstance(Comment.class), postId);
    }

    @Override
    public Comment updatePost(Comment comment) {
        jdbcTemplate.update(UPDATE_COMMENT_BY_ID_QUERY, comment.getComment(), comment.getId());
        return comment;
    }


    @Override
    public Comment findCommentById(Long commentId) {
        return jdbcTemplate.queryForObject(GET_COMMENT_BY_ID_QUERY,
                BeanPropertyRowMapper.newInstance(Comment.class), commentId);
    }

    @Override
    public List<Comment> findCommentByComment(String comment) {
        return jdbcTemplate.query(GET_COMMENT_BY_COMMENTS_QUERY,
                BeanPropertyRowMapper.newInstance(Comment.class), comment);
    }

    @Override
    public String deleteCommentById(Long commentId) {
        jdbcTemplate.update(DELETE_COMMENT_BY_ID, commentId);
        return "Comments got deleted";
    }
}
