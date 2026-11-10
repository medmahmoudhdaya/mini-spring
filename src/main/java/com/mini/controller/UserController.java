package com.mini.controller;

import com.mini.annotation.GetMapping;
import com.mini.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/users")
    public String getUsers() {
        return "Users endpoint from UserController";
    }

    @GetMapping("/users/profile")
    public String getProfile() {
        return "User profile";
    }
}