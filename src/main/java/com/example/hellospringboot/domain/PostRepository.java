package com.example.hellospringboot.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * PostRepository class
 *
 * @author gukt
 * @since 1.0
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
