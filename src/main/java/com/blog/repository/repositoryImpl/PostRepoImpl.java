package com.blog.repository.repositoryImpl;

import com.blog.model.Post;
import com.blog.model.User;
import com.blog.pojo.FavouritePostDto;
import com.blog.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepoImpl implements PostRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String INSERT_POST_QUERY = "INSERT INTO POSTS(title,body,user_id) values(?,?,?)";
    private static final String INSERT_FAVOURITE_POST_QUERY = "INSERT INTO FAVOURITE_POSTS(post_id,post_user_id,user_id) values(?,?,?)";
    private static final String UPDATE_POST_BY_ID_QUERY = "UPDATE POSTS SET title=?,body=?,user_id=? WHERE ID=?";
    private static final String GET_POST_BY_ID_QUERY = "SELECT * FROM POSTS WHERE ID=?";
    private static final String GET_POST_BY_USERID_QUERY = "SELECT * FROM POSTS WHERE USER_ID=?";
    private static final String GET_FAVOURITE_POST_BY_USER_ID_QUERY = "SELECT * FROM FAVOURITE_POSTS WHERE USER_ID=?";
    private static final String GET_POST_QUERY = "SELECT * FROM POSTS";
    private static final String DELETE_POST_BY_ID = "DELETE FROM POSTS WHERE ID=?";

    @Override
    public void savePost(Post post) {
        jdbcTemplate.update(INSERT_POST_QUERY,post.getTitle(), post.getBody(), post.getUser().getId());
    }

    @Override
    public void saveFavouritePost(FavouritePostDto favouritePostDto) {
        jdbcTemplate.update(INSERT_FAVOURITE_POST_QUERY,favouritePostDto.getPostId(), favouritePostDto.getPostUserId(), favouritePostDto.getUserId());
    }

    @Override
    public Post updatePost(Post post) {
        jdbcTemplate.update(UPDATE_POST_BY_ID_QUERY, post.getTitle(), post.getBody(),post.getUser().getId(), post.getId());
        return post;
    }

    @Override
    public Post getPostById(long postId) {
        return jdbcTemplate.queryForObject(GET_POST_BY_ID_QUERY,
                BeanPropertyRowMapper.newInstance(Post.class), postId);
    }

    @Override
    public List<Post> getAllPostById(long userId) {
        return  jdbcTemplate.query(GET_POST_BY_USERID_QUERY,
                BeanPropertyRowMapper.newInstance(Post.class), userId);
    }

    @Override
    public List<FavouritePostDto> getAllFavouritePostByUserId(long userId) {
        return  jdbcTemplate.query(GET_FAVOURITE_POST_BY_USER_ID_QUERY,
                BeanPropertyRowMapper.newInstance(FavouritePostDto.class), userId);
//        return jdbcTemplate.query(GET_FAVOURITE_POST_BY_USER_ID_QUERY, (rs, rowNum) -> {
//            return new Post(rs.getLong("original_post_id"), rs.getString("title"),
//                    rs.getString("body"), rs.getTimestamp("create_time"),
//                    rs.getLong("post_user_id"), rs.getLong("user_id"));
//        },userId);
    }

    @Override
    public List<Post> getAllPost() {
        return  jdbcTemplate.query(GET_POST_QUERY,
                BeanPropertyRowMapper.newInstance(Post.class));
    }

//    @Override
//    public Post getById(int postId){
//        return jdbcTemplate.queryForObject(GET_POST_BY_ID_QUERY, (rs, rowNum) -> {
//            return new Post(rs.getLong("id"), rs.getString("title"), rs.getString("body"), rs.getString("name"), rs.getLong("user_id"));
//        },postId);
//    }



    @Override
    public String deleteById(long id) {
        jdbcTemplate.update(DELETE_POST_BY_ID, id);
        return "Post got deleted with id " + id;
    }
}
