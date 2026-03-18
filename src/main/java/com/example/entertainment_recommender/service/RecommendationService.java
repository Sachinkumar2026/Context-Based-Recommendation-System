package com.example.entertainment_recommender.service;

import com.example.entertainment_recommender.model.Content;
import com.example.entertainment_recommender.model.UserPreference;
import com.example.entertainment_recommender.model.WatchHistory;
import com.example.entertainment_recommender.repository.ContentRepository;
import com.example.entertainment_recommender.repository.UserPreferenceRepository;
import com.example.entertainment_recommender.repository.WatchHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@AllArgsConstructor
public class RecommendationService {

    private final ContentRepository contentRepository;
    private final UserPreferenceRepository userPreferenceRepository;
    private final WatchHistoryRepository watchHistoryRepository;


    @Cacheable(value = "recommendations",
            key = "#userId + '-' + #duration + '-' + #platform + '-' + #context + '-' + #watchingWith")
    public List<Content> recommendContent(Long userId, int duration, String platform,String context,String watchingWith) {

        UserPreference pref = userPreferenceRepository
                .findByUser_Id(userId)
                .orElse(null);

        List<Content> contentList =
                contentRepository.findByPlatformAndDurationLessThanEqual(platform,duration);
        List<WatchHistory> historyList =
                watchHistoryRepository.findByUser_Id(userId);
        Map<String,Integer> genreCount = new HashMap<>();

        for (WatchHistory h : historyList){
            String genre = h.getContent().getGenre();
            genreCount.put(genre,genreCount.getOrDefault(genre,0) + 1);
        }

        return contentList.stream()
                .sorted((c1,c2) -> Double.compare(
                        score(c2,duration,pref,context,watchingWith,genreCount),
                        score(c1,duration,pref,context,watchingWith,genreCount)))
                .limit(10)
                .toList();
    }

    private double score(Content content,int requestedDuration, UserPreference pref,String context,String watchingWith,Map<String,Integer>genreCount){

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
        if(watchingWith != null){
            if(watchingWith.equalsIgnoreCase("family") &&
            content.getGenre().equalsIgnoreCase("comedy"))
                score += 2;

            if(watchingWith.equalsIgnoreCase("friends") &&
            content.getGenre().equalsIgnoreCase("comedy"))
                score += 1;
        }
        if(context != null){
            if(context.equalsIgnoreCase("lunch") && content.getDuration() <= 20)
                score += 2;

            if(context.equalsIgnoreCase("late-night") &&
            content.getGenre().equalsIgnoreCase("thriller"))
                score += 2;
        }

        if(genreCount.containsKey(content.getGenre())){
            score += genreCount.get(content.getGenre());
        }

        return score;
    }
}
