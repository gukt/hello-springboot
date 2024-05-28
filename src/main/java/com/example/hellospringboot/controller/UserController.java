package com.example.hellospringboot.controller;

import com.example.hellospringboot.domain.User;
import com.example.hellospringboot.domain.UserRepository;
import com.example.hellospringboot.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
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
    private final UserService userService;

    @GetMapping("/users")
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/profile")
    public Object userProfile(HttpServletRequest request) {
        long userId = userService.checkLogin(request);

        MDC.put("userId", String.valueOf(userId));
        MDC.put("username", "gukt");
        log.info("This is a log message with user info.");

        return "user profile";
    }
}
