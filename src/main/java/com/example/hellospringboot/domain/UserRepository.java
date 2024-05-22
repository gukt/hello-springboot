package com.example.hellospringboot.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository class
 *
 * @author gukt
 * @since 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
