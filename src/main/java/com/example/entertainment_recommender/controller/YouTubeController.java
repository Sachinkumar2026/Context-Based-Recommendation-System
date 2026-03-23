package com.example.entertainment_recommender.controller;

import com.example.entertainment_recommender.service.YouTubeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/youtube")
@AllArgsConstructor
public class YouTubeController {

    private final YouTubeService youTubeService;

    @PostMapping("/fetch")
    public String fetch(){
        youTubeService.fetchTrendingVideos();
        return "Fetched!";
    }

}
