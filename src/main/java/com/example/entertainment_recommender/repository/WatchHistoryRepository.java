package com.example.entertainment_recommender.repository;

import com.example.entertainment_recommender.model.WatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WatchHistoryRepository extends JpaRepository<WatchHistory,Long> {

    List<WatchHistory> findByUserId(Long userId);
}
