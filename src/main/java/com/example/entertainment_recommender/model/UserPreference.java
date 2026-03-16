package com.example.entertainment_recommender.model;

import jakarta.persistence.*;

@Entity
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
