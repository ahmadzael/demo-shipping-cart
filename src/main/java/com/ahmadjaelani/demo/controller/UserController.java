package com.ahmadjaelani.demo.controller;

import com.ahmadjaelani.demo.entity.User;
import com.ahmadjaelani.demo.model.RegisterRequest;
import com.ahmadjaelani.demo.model.UpdateUserRequest;
import com.ahmadjaelani.demo.model.UserResponse;
import com.ahmadjaelani.demo.model.WebResponse;
import com.ahmadjaelani.demo.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserServices userService;

    @PostMapping(
            path = "/api/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> register(@RequestBody RegisterRequest request) {
        userService.register(request);
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(
            path = "/api/users/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> get(String userId) {
        UserResponse userResponse = userService.get(userId);
        return WebResponse.<UserResponse>builder().data(userResponse).build();
    }

    @GetMapping(path = "api/users")
    public WebResponse<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUser();
        return  WebResponse.<List<UserResponse>>builder().data(users).build();
    }

    @PatchMapping(
            path = "/api/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> update(User user, @RequestBody UpdateUserRequest request) {
        UserResponse userResponse = userService.update(user, request);
        return WebResponse.<UserResponse>builder().data(userResponse).build();
    }
}
