package com.example.entertainment_recommender.controller;

import com.example.entertainment_recommender.dto.RecommendationRequest;
import com.example.entertainment_recommender.model.Content;
import com.example.entertainment_recommender.service.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
@AllArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping
    public List<Content> getRecommendation(
            @RequestBody RecommendationRequest request
            ){
        return recommendationService.recommendContent(
                request.getUserId(),
                request.getDuration(),
                request.getPlatform(),
                request.getContext(),
                request.getWatchingWith()
        );
    }

}
