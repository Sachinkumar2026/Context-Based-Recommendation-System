package com.example.entertainment_recommender.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendationRequest {

    private Long userId;
    private int duration;
    private String platform;
}
