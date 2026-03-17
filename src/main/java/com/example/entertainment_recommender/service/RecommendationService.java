package com.example.entertainment_recommender.service;

import com.example.entertainment_recommender.model.Content;
import com.example.entertainment_recommender.model.UserPreference;
import com.example.entertainment_recommender.repository.ContentRepository;
import com.example.entertainment_recommender.repository.UserPreferenceRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@AllArgsConstructor
public class RecommendationService {

    private final ContentRepository contentRepository;
    private final UserPreferenceRepository userPreferenceRepository;

    public List<Content> recommendContent(Long userId, int duration, String platform) {

        UserPreference pref = userPreferenceRepository
                .findByUser_Id(userId)
                .orElse(null);

        List<Content> contentList =
                contentRepository.findByPlatformAndDurationLessThanEqual(platform,duration);

        return contentList.stream()
                .sorted((c1,c2) -> Double.compare(
                        score(c2,duration,pref),
                        score(c1,duration,pref)))
                .limit(10)
                .toList();
    }

    private double score(Content content,int requestedDuration, UserPreference pref){

        double score = 0;

        if (content.getRating() >= 8)
            score += 2;

        if(Math.abs(content.getDuration() - requestedDuration) <= 5)
            score += 1;

        if (content.getReleaseYear() >= 2020)
            score += 1;

        if(pref != null){

            if(pref.getFavoriteGenres() != null &&
                    pref.getFavoriteGenres().contains(content.getGenre()))
                score += 3;

            if(pref.getAvoidGenres() != null &&
                    pref.getAvoidGenres().contains(content.getGenre()))
                score -= 3;
        }

        return score;
    }
}
