package com.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {


    private long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Timestamp createTime;
    private String contact;
}
