package com.example.entertainment_recommender.service;


import com.example.entertainment_recommender.model.Content;
import com.example.entertainment_recommender.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.Result;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TmdbService {

    private final RestTemplate restTemplate;
    private final ContentRepository contentRepository;

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Transactional
    public void fetchTrendingMovies(){

        String url = "https://api.themoviedb.org/3/trending/movie/day?api_key=" + apiKey;
        Map response = restTemplate.getForObject(url, Map.class);

        List<Map<String,Object>> results =
                (List<Map<String, Object>>) response.get("results");


        for(Map<String,Object> movie : results){
            String title = (String) movie.get("title");
            Double rating = ((Number) movie.get("vote_average")).doubleValue();
            String releaseDate = (String) movie.get("release_date");

            int year = 2024;
            if(releaseDate != null && releaseDate.length() >= 4){
                year = Integer.parseInt(releaseDate.substring(0,4));
            }

            Content content = new Content();
            content.setTitle(title);
            content.setPlatform("tmdb");
            content.setGenre("movie");
            content.setDuration(140);
            content.setRating(rating);
            content.setReleaseYear(year);
            System.out.println("Saving movie: " + title);

            try {
                contentRepository.save(content);
                System.out.println("Saved: " + title);
            } catch (Exception e) {
                System.out.println("ERROR saving: " + e.getMessage());
            }
        }
        System.out.println("Total movies: " + results.size());
    }

}
