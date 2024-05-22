package com.example.hellospringboot.service;

import com.example.hellospringboot.domain.PostRepository;
import com.example.hellospringboot.domain.UserRepository;
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
public class PostService {

    private final PostRepository postRepository;


}
