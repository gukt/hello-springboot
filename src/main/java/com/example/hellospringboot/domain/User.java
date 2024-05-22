package com.example.hellospringboot.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * User class
 *
 * @author gukt
 * @since 1.0
 */
@Entity
@Table(name = "t_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
