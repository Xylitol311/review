package com.example.review.controller;

import com.example.review.dto.PostCreatRequestDto;
import com.example.review.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @PostMapping("/create")
    public String postCreate(@RequestBody @Valid PostCreatRequestDto postCreatRequestDto) {
        return postService.createPost(postCreatRequestDto);
    }
}
