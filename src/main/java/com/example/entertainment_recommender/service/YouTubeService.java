package com.example.entertainment_recommender.service;


import com.example.entertainment_recommender.model.Content;
import com.example.entertainment_recommender.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class YouTubeService {

    private final RestTemplate restTemplate;
    private final ContentRepository contentRepository;
    @Value("${youtube.api.key}")
    private String apiKey;

    public void fetchTrendingVideos(){

        String url = "https://www.googleapis.com/youtube/v3/videos"
                + "?part=snippet,contentDetails"
                + "&chart=mostPopular"
                + "&maxResults=10"
                + "&regionCode=IN"
                + "&key=" + apiKey;


        Map response = restTemplate.getForObject(url, Map.class);

        List<Map<String, Object>> items =
                (List<Map<String, Object>>) response.get("items");

        for(Map<String,Object> item : items){

            Map snippet = (Map) item.get("snippet");

            String title = (String) snippet.get("title");

            Content content = new Content();
            content.setTitle(title);
            content.setPlatform("youtube");
            content.setGenre("general");
            content.setDuration(10);
            content.setRating(7.0);
            content.setReleaseYear(2024);

            contentRepository.save(content);
        }

    }
}
