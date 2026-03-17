package com.example.entertainment_recommender.repository;

import com.example.entertainment_recommender.model.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPreferenceRepository extends JpaRepository<UserPreference,Long> {
    Optional<UserPreference> findByUser_Id(Long user);
}
