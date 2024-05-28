package com.example.hellospringboot.service;

import com.example.hellospringboot.domain.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * UserService class
 *
 * @author gukt
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public long checkLogin(HttpServletRequest request) {
        return 123L; // for testing only
    }
}
