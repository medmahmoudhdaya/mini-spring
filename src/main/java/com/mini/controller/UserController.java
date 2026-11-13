package com.mini.controller;

import com.mini.annotation.GetMapping;
import com.mini.annotation.PostMapping;
import com.mini.annotation.RestController;
import com.mini.service.UserService;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<String> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/users")
    public String createUser() {
        return userService.createUser();
    }

    @GetMapping("/users/profile")
    public String getProfile() {
        return "User profile";
    }
}