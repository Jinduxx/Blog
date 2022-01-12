package com.blog.Service.serviceImpl;

import com.blog.Service.FriendService;
import com.blog.model.User;
import com.blog.pojo.FriendDto;
import com.blog.repository.repositoryImpl.FriendsRepoImpl;
import com.blog.repository.repositoryImpl.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {

    private final FriendsRepoImpl friendsRepo;
    private final UserRepoImpl userRepo;

    @Autowired
    public FriendServiceImpl(FriendsRepoImpl friendsRepo, UserRepoImpl userRepo) {
        this.friendsRepo = friendsRepo;
        this.userRepo = userRepo;
    }

    @Override
    public boolean createFriend(long friendUserId, long userId) {
        boolean result;
        User friend = userRepo.getById(friendUserId);
        User user = userRepo.getById(userId);
        System.out.println(friend);
        if(friend != null){
            System.out.println(friend);
            friendsRepo.saveFriend(friend, user);
            result = true;
        } else{
            result = false;
        }
        return  result;
    }

    @Override
    public List<FriendDto> getFriendByUserId(long userId) {
        List<FriendDto> userInfo = friendsRepo.getByUserId(userId);
        if(userInfo.isEmpty()){
            userInfo = null;
        }
        return userInfo;
    }

    @Override
    public FriendDto getFriendByUserIdAndFriendId(long userId, long friendId) {
       return friendsRepo.getByUserIdAndFriendId(userId, friendId);
    }

    @Override
    public String deleteFriend(long friendId, long userId) {
        FriendDto friendDto  = friendsRepo.getByUserIdAndFriendId(userId, friendId);
        String deletedUser = null;
        if(friendDto != null){
            deletedUser = friendsRepo.deleteByFriendId(friendId, userId);
        }
        return deletedUser;
    }
}
