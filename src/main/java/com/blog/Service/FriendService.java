package com.blog.Service;

import com.blog.pojo.FriendDto;

import java.util.List;

public interface FriendService {

    boolean createFriend(long friendUserId, long userId);
    List<FriendDto> getFriendByUserId(long userId);
    Object getFriendByUserIdAndFriendId(long userId, long friendId);
    String deleteFriend(long friendId, long userId);
}
