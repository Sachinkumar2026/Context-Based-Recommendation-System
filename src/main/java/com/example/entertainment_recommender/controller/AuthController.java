package com.example.entertainment_recommender.controller;


import com.example.entertainment_recommender.dto.AuthResponse;
import com.example.entertainment_recommender.dto.LoginRequest;
import com.example.entertainment_recommender.dto.SignupRequest;
import com.example.entertainment_recommender.model.User;
import com.example.entertainment_recommender.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public User signup(@RequestBody SignupRequest request){
        return authService.signup(
                request.getEmail(),
                request.getPassword()
        );
    }


    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request){

        String token = authService.login(
                request.getEmail(),
                request.getPassword()
        );
        return new AuthResponse(token);
    }
}
