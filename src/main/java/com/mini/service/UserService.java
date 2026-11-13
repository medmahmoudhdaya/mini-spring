package com.mini.service;

import com.mini.annotation.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    public List<String> getUsers() {
        List<String> usersList =  new ArrayList<>() ;

        usersList.add("User 1");
        usersList.add("User 2");
        usersList.add("User 3");

        return usersList;
    }

    public String createUser() {
        return "User created successfuly.";
    }
}