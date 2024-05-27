package com.ahmadjaelani.demo.services;

import com.ahmadjaelani.demo.entity.Product;
import com.ahmadjaelani.demo.entity.User;
import com.ahmadjaelani.demo.model.RegisterRequest;
import com.ahmadjaelani.demo.model.UpdateUserRequest;
import com.ahmadjaelani.demo.model.UserResponse;
import com.ahmadjaelani.demo.repository.UserRepository;
import com.ahmadjaelani.demo.security.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;


    @Transactional
    public void register(RegisterRequest request) {

        if (userRepository.existsById(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already registered");
        }

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setName(request.getName());
        user.setAddress(request.getAddress());

        userRepository.save(user);
    }

    public UserResponse get(String userId) {
        Optional<User> user = userRepository.findById(userId);

        System.out.println((user.get().getUsername()));

        return UserResponse.builder()
                .username(user.get().getUsername())
                .name(user.get().getName())
                .id(user.get().getId())
                .address(user.get().getAddress())
                .build();
    }

    public List<UserResponse> getAllUser(){
        List<User> listUser = userRepository.findAll();

        return  listUser.stream().map(this::toUserRespnse).toList();
    }

    @Transactional
    public UserResponse update(User user, UpdateUserRequest request) {

        if (Objects.nonNull(request.getName())) {
            user.setName(request.getName());
        }

        if (Objects.nonNull(request.getPassword())) {
            user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        }

        if (Objects.nonNull(request.getUsername())) {
            user.setUsername(request.getUsername());
        }

        if (Objects.nonNull(request.getAddress())) {
            user.setAddress(request.getAddress());
        }

        user.setId(request.getId());

        userRepository.save(user);

        return UserResponse.builder()
                .name(user.getName())
                .username(user.getUsername())
                .address(user.getAddress())
                .id(user.getId())
                .address(user.getAddress())
                .build();
    }

    private UserResponse toUserRespnse(User user){
        return UserResponse.builder()
                .name(user.getName())
                .username(user.getUsername())
                .address(user.getAddress())
                .id(user.getId())
                .build();
    }

    @Transactional
    public void delete(String id){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));

        userRepository.delete(user);
    }
}
