package com.example.entertainment_recommender.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;




@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true,nullable = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private LocalDateTime createdAt;
}
