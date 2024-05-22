package com.example.hellospringboot.controller;

import com.example.hellospringboot.domain.User;
import com.example.hellospringboot.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * UserController class
 *
 * @author gukt
 * @since 1.0
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/users")
    public List<User> listUsers() {
        return userRepository.findAll();
    }
}
