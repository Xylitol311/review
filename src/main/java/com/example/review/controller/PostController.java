package com.example.review.controller;

import com.example.review.dto.PostCreatRequestDto;
import com.example.review.dto.PostUpdateRequestDto;
import com.example.review.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PostUpdate;
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
    
    @PostMapping("/update")
    public String postUpdate(@RequestParam Long postId, @RequestBody @Valid PostUpdateRequestDto postUpdateRequestDto) {
        return postService.updatePost(postId, postUpdateRequestDto);
    }
}
