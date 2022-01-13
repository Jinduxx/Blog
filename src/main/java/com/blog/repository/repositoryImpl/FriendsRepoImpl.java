package com.blog.repository.repositoryImpl;

import com.blog.model.User;
import com.blog.pojo.FriendDto;
import com.blog.repository.FriendsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FriendsRepoImpl implements FriendsRepo {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendsRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String INSERT_FRIENDS_QUERY = "INSERT INTO FRIENDS(friends_id,user_id) values(?,?)";
    private static final String GET_FRIENDS_BY_USER_ID_QUERY = "SELECT * FROM FRIENDS WHERE USER_ID=?";
    private static final String GET_FRIENDS_BY_USER_ID_AND_FRIENDS_ID_QUERY = "SELECT * FROM FRIENDS WHERE USER_ID=? AND FRIENDS_ID=?";
    private static final String DELETE_FRIENDS_BY_FRIENDS_ID_AND_USER_ID = "DELETE FROM FRIENDS WHERE FRIENDS_ID=? AND USER_ID=?";

    @Override
    public void saveFriend(User user1, User user2) {
        jdbcTemplate.update(INSERT_FRIENDS_QUERY, user1.getId(), user2.getId());
    }

    @Override
    public List<FriendDto> getByUserId(long userId) {
        List<FriendDto> friendDto = new ArrayList<>();
        try {
            friendDto = (List<FriendDto>) jdbcTemplate.query( GET_FRIENDS_BY_USER_ID_QUERY,
                    BeanPropertyRowMapper.newInstance(FriendDto.class), userId);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        if(friendDto.size() == 0){
            return null;
        }
        return friendDto;
    }

    @Override
    public FriendDto getByUserIdAndFriendId(long userId, long friendId) {
        List<FriendDto> friendDto = new ArrayList<>();
        try {
            friendDto = (List<FriendDto>) jdbcTemplate.query(GET_FRIENDS_BY_USER_ID_AND_FRIENDS_ID_QUERY, (rs, rowNum) ->
                    new FriendDto(rs.getLong("id"), rs.getLong("friends_id"),
                            rs.getLong("user_id")),userId,friendId);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        System.out.println(friendDto);
        if(friendDto.size() == 0){
            return null;
        }
        return friendDto.get(0);
    }

    @Override
    public String deleteByFriendId(long friendId, long userId) {
        jdbcTemplate.update(DELETE_FRIENDS_BY_FRIENDS_ID_AND_USER_ID, friendId,userId);
        return "Friend got deleted with id " + friendId;
    }
}
