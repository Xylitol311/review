package com.example.review.controller;

import com.example.review.dto.PostDeleteRequestDto;
import com.example.review.dto.PostInputDto;
import com.example.review.dto.PostListResponseDto;
import com.example.review.dto.PostResponseDto;
import com.example.review.service.PostService;
import com.example.review.type.PostCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    
    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody @Valid PostInputDto postInputDto) {
        postService.createPost(postInputDto);
        return ResponseEntity.ok("success");
    }
    
    @PostMapping("/update/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody @Valid PostInputDto postInputDto) {
        postService.updatePost(postId, postInputDto);
        return ResponseEntity.ok("success");
    }
    
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId, @RequestBody @Valid PostDeleteRequestDto postDeleteRequestDto) {
        postService.deletePost(postId, postDeleteRequestDto);
        return ResponseEntity.ok("success");
    }
    
    @GetMapping
    public List<PostListResponseDto> findAllPost() {
        return postService.findAll();
    }
    
    @GetMapping("/search/title")
    public List<PostListResponseDto> findPostByTitle(@RequestParam String title) {
        return postService.findByTitle(title);
    }
    
    @GetMapping("/search/category")
    public List<PostListResponseDto> findPostByCategory(@RequestParam PostCategory category) {
        return postService.findByCategory(category);
    }
    
    @GetMapping("search/nickname")
    public List<PostListResponseDto> findPostByNickname(@RequestParam String nickname) {
        return postService.findByNickname(nickname);
    }
    
    @GetMapping("search/date")
    public List<PostListResponseDto> findPostByCreatedDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdDate) {
        return postService.findByCreatedDate(createdDate);
    }
    
    @GetMapping("/search/memberId")
    public List<PostListResponseDto> findPostByMemberId(@RequestParam Long memberId) {
        return postService.findByMemberId(memberId);
    }
    
    @GetMapping("/read/{postId}")
    public PostResponseDto readPost(@PathVariable Long postId) {
        return postService.readPost(postId);
    }
}
