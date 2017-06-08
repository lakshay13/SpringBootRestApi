package com.suri.springboot.service;

import java.util.List;

import com.suri.springboot.model.Users;
import org.springframework.stereotype.Component;

/**
 * @author lakshay13@gmail.com
 */
@Component
public class UserService {

    private List<Users> usersList;

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    public List<Users> getUsersList() {
        return usersList;
    }
}
