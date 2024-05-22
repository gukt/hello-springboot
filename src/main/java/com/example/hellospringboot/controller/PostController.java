package com.example.hellospringboot.controller;

import com.example.hellospringboot.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PostController class
 *
 * @author gukt
 * @since 1.0
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;
}
