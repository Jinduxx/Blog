package com.blog.repository;

import com.blog.model.User;

public interface UserRepo {
    void saveUser(User user);

    User updateUser(User user);

    User getByEmail(String email);

    User getByUseName(String userName);

    User getById(long id);

    String deleteById(long id);

//    List<User> allUsers();
}
