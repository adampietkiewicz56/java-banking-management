package com.pietkiewicz.bankapp.controller;

import com.pietkiewicz.bankapp.entity.User;
import com.pietkiewicz.bankapp.service.UserService;
import org.springframework.web.bind.annotation.*;
import com.pietkiewicz.bankapp.dto.CreateUserRequest;
import jakarta.validation.Valid;
import java.util.List;
import com.pietkiewicz.bankapp.dto.LoginRequest;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody CreateUserRequest request) {
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return userService.createUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return userService.login(request.getEmail(), request.getPassword());
    }
}