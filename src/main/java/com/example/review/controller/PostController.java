package com.example.review.controller;

import com.example.review.dto.PostDeleteRequestDto;
import com.example.review.dto.PostInputDto;
import com.example.review.dto.PostListResponseDto;
import com.example.review.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    
    @PostMapping("/create")
    public String postCreate(@RequestBody @Valid PostInputDto postInputDto) {
        return postService.createPost(postInputDto);
    }
    
    @PostMapping("/update")
    public String postUpdate(@RequestParam Long postId, @RequestBody @Valid PostInputDto postInputDto) {
        return postService.updatePost(postId, postInputDto);
    }
    
    @DeleteMapping("/delete")
    public String postDelete(@RequestParam Long postId, @RequestBody @Valid PostDeleteRequestDto postDeleteRequestDto) {
        return postService.deletePost(postId, postDeleteRequestDto);
    }
    
    @GetMapping
    public List<PostListResponseDto> postFindAll() {
        return postService.findAll();
    }
}
