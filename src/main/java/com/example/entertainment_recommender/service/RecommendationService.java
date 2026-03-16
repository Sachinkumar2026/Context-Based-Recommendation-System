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

       List<Content> contentList =
               contentRepository.findByPlatformAndDurationLessThanEqual(platform,duration);

       return contentList.stream()
               .sorted((c1,c2) -> Double.compare(score(c2,duration),score(c1,duration)))
               .limit(10)
               .toList();
    }

    private double score(Content content,int requestedDuration){
        double score = 0;

        if (content.getRating() >= 8)
            score += 2;

        if(Math.abs(content.getDuration() - requestedDuration) <= 5)
            score += 1;

        if (content.getReleaseYear() >= 2020)
            score += 1;

        return score;
    }
}
