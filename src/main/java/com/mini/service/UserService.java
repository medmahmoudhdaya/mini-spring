package com.mini.service;

import com.mini.annotation.Service;

@Service
public class UserService {

    public String getUsers() {
        return "Users returned from UserService";
    }

    public String createUser() {
        return "User created successfuly.";
    }
}