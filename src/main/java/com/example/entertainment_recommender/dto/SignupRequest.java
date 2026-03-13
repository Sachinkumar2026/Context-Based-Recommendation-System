package com.example.entertainment_recommender.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequest {

    private String email;
    private String password;
}
