package com.mini.controller;

import com.mini.annotation.GetMapping;
import com.mini.annotation.RestController;
import com.mini.service.UserService;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/users/profile")
    public String getProfile() {
        return "User profile";
    }
}