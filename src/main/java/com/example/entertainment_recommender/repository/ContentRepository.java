package com.example.entertainment_recommender.repository;

import com.example.entertainment_recommender.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content,Long> {

    List<Content> findByPlatform(String platform);

    List<Content> findByDurationLessThanEqual(int duration);

    List<Content> findByPlatformAndDurationLessThanEqual(String platform, int duration);
}
