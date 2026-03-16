package com.example.entertainment_recommender.model;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreference {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String favoriteGenres;

    private String avoidGenres;

    private String language;

    private String ottSubscription;


    @OneToOne
    private User user;
}
