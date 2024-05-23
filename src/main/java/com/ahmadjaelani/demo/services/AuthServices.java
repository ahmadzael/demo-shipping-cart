package com.ahmadjaelani.demo.services;

import com.ahmadjaelani.demo.entity.User;
import com.ahmadjaelani.demo.model.LoginRequest;
import com.ahmadjaelani.demo.model.TokenResponse;
import com.ahmadjaelani.demo.repository.UserRepository;
import com.ahmadjaelani.demo.security.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AuthServices {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public TokenResponse login(LoginRequest request){
        //validationService.validate(request);

        User user = userRepository.findById(request.getUsername())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Username Or Pasword wrong"));

        if (BCrypt.checkpw(request.getPassword(),user.getPassword())){
            user.setToken(UUID.randomUUID().toString());
            user.setTokenExpiredAt(next30Days());
            userRepository.save(user);

            return TokenResponse.builder()
                    .token(user.getToken())
                    .expiredAt(user.getTokenExpiredAt())
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Username Or Pasword wrong");
        }

    }
    @Transactional
    public void logout(User user){
        user.setToken(null);
        user.setTokenExpiredAt(null);

        userRepository.save(user);
    }

    private Long next30Days(){
        return System.currentTimeMillis() + (1000*16*24*30);
    }

}
