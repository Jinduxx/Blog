package com.blog.repository.repositoryImpl;

import com.blog.model.User;
import com.blog.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepoImpl implements UserRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String INSERT_USER_QUERY = "INSERT INTO USERS(user_name,first_name,last_name,email,password,contact) values(?,?,?,?,?,?)";
    private static final String UPDATE_USER_BY_ID_QUERY = "UPDATE USERS SET user_name=?,first_name=?,last_name=?,email=?,password=?,contact=? WHERE ID=?";
    private static final String GET_USER_BY_ID_QUERY = "SELECT * FROM USERS WHERE ID=?";
    private static final String GET_USER_BY_EMAIL_QUERY = "SELECT * FROM USERS WHERE EMAIL=?";
    private static final String GET_USER_BY_USERNAME_QUERY = "SELECT * FROM USERS WHERE USER_NAME=?";
    private static final String DELETE_USER_BY_ID = "DELETE FROM USERS WHERE ID=?";
//    private static final String GET_USERS_QUERY = "SELECT * FROM USERS";

    @Override
    public void saveUser(User user) {
        jdbcTemplate.update(INSERT_USER_QUERY,user.getUserName(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getContact());
    }

    @Override
    public User updateUser(User user) {
        jdbcTemplate.update(UPDATE_USER_BY_ID_QUERY, user.getUserName(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getContact(), user.getId());
        return user;
    }

    @Override
    public User getByEmail(String email) {
        List<User> user = new ArrayList<>();
        try {
            user = (List<User>) jdbcTemplate.query( GET_USER_BY_EMAIL_QUERY,
                    BeanPropertyRowMapper.newInstance(User.class), email);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        if(user.size() == 0){
            return null;
        }
        return user.get(0);
    }

    @Override
    public User getByUseName(String userName) {
        List<User> user = new ArrayList<>();
        try {
            user = (List<User>) jdbcTemplate.query( GET_USER_BY_USERNAME_QUERY,
                    BeanPropertyRowMapper.newInstance(User.class), userName);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        if(user.size() == 0){
            return null;
        }
        return user.get(0);
    }


//    @Override
//    public boolean isEmailIdExists(String email) {
//        String sql = "SELECT count(*) FROM table WHERE email = ?";
//
//        int count = jdbcTemplate.queryForObject(sql, new Object[] { email }, Integer.class);
//
//        return count > 0;
//    }

    @Override
    public User getById(long id) {
        return jdbcTemplate.queryForObject(GET_USER_BY_ID_QUERY, (rs, rowNum) -> {
            return new User(rs.getLong("id"), rs.getString("user_name"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("password"), rs.getTimestamp("create_time"), rs.getString("contact"));
        },id);
    }

    @Override
    public String deleteById(long id) {
        jdbcTemplate.update(DELETE_USER_BY_ID, id);
        return "User got deleted with id " + id;
    }

//    @Override
//    public List<User> allUsers() {
//        return jdbcTemplate.query(GET_USERS_QUERY, (rs, rowNum) -> {
//            return new User(rs.getLong("id"), rs.getString("user_name"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("password"), rs.getTimestamp("createtime"), rs.getString("contact"));
//        });
//    }
}
