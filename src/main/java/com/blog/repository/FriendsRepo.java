package com.blog.repository;

import com.blog.model.User;
import com.blog.pojo.FriendDto;

import java.util.List;

public interface FriendsRepo {

    void saveFriend(User user1, User user2);
    List<FriendDto> getByUserId(long userId);
    Object getByUserIdAndFriendId(long userId, long friendId);
    String deleteByFriendId(long friendId, long userId);
}
