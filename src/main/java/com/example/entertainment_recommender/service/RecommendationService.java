package com.example.entertainment_recommender.service;

import com.example.entertainment_recommender.model.Content;
import com.example.entertainment_recommender.repository.ContentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class RecommendationService {
    private final ContentRepository contentRepository;


    public List<Content> recommendContent(int duration,String platform){

        List<Content> contentList = contentRepository
                .findByDurationLessThanEqual(duration);

        return contentList.stream()
                .filter(content -> content.getPlatform().equalsIgnoreCase(platform))
                .limit(10)
                .toList();
    }
}
