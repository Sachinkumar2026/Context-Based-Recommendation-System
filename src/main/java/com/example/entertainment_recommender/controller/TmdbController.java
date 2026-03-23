package com.example.entertainment_recommender.controller;

import com.example.entertainment_recommender.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tmdb")
@RequiredArgsConstructor
public class TmdbController {

    private final TmdbService tmdbService;

    @PostMapping("/fetch")
    public String fetchMovies(){
        System.out.println("TMDB CONTROLLER HIT");
        tmdbService.fetchTrendingMovies();
        return "Movies fetched!";
    }
}
