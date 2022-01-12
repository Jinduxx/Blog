package com.blog.pojo;

import com.blog.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendDto extends User {

    private long id;
    private long friendsId;
    private long userId;

}
