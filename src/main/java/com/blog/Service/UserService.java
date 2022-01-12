package com.blog.Service;

import com.blog.model.User;

public interface UserService {

    boolean createUser(User user);
    User getUser(String email, String password);
    User getUserById(Long id);
    User getUserByUsername(String userName);
    User updateUser(User user);
    String deleteUser(long id);
//    User findUsers(Long userId);
}
