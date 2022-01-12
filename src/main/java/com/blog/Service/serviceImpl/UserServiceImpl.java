package com.blog.Service.serviceImpl;

import com.blog.Service.UserService;
import com.blog.model.User;
import com.blog.repository.repositoryImpl.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepoImpl userRepo;

    @Autowired
    public UserServiceImpl(UserRepoImpl userRepo) {
        this.userRepo = userRepo;
    }


    @Override
    public boolean createUser(User user) {
        boolean result;
        User userInfo = userRepo.getByEmail(user.getEmail());
        System.out.println(userInfo);
        if(userInfo == null){
            System.out.println(user);
            userRepo.saveUser(user);
            result = true;
        } else{
            result = false;
        }
        return  result;
    }

    @Override
    public User getUser(String email, String password) {
        User userInfo;
        userInfo = userRepo.getByEmail(email);
        if(userInfo == null){
            userInfo = null;
        } else{
            if(!password.equals(userInfo.getPassword())){
                userInfo = null;
            }
        }
        return userInfo;
    }

    @Override
    public User getUserByUsername(String userName) {
        return userRepo.getByUseName(userName);
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.getById(id);
    }


    @Override
    public User updateUser(User user) {
        return userRepo.updateUser(user);
    }

    @Override
    public String deleteUser(long id) {
        User user  = userRepo.getById(id);
        String deletedUser = null;
        if(user != null){
            deletedUser = userRepo.deleteById(id);
        }
        return deletedUser;
    }

//    @Override
//    public User findUsers(Long userId) {
//        return null;
//    }
}
