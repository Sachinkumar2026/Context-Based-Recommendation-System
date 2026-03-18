package com.example.entertainment_recommender.service;


import com.example.entertainment_recommender.model.Content;
import com.example.entertainment_recommender.model.User;
import com.example.entertainment_recommender.model.WatchHistory;
import com.example.entertainment_recommender.repository.ContentRepository;
import com.example.entertainment_recommender.repository.UserRepository;
import com.example.entertainment_recommender.repository.WatchHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class WatchHistoryService {

    private final WatchHistoryRepository watchHistoryRepository;
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;


    @CacheEvict(value = "recommendations", allEntries = true)
    public void saveWatch(Long userId,Long contentId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Content content = contentRepository.findById(contentId)
                .orElseThrow(() ->new RuntimeException("Content not found"));

        WatchHistory history = WatchHistory.builder()
                .user(user)
                .content(content)
                .watchedAt(LocalDateTime.now())
                .build();

        watchHistoryRepository.save(history);
    }
}
