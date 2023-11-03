package com.example.review.controller;

import com.example.review.dto.PostInputDto;
import com.example.review.dto.PostDeleteRequestDto;
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
    
    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody @Valid PostInputDto postInputDto) {
        postService.updatePost(postId, postInputDto);
        return ResponseEntity.ok("success");
    }
    
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId, @RequestBody @Valid PostDeleteRequestDto postDeleteRequestDto) {
        postService.deletePost(postId, postDeleteRequestDto);
        return ResponseEntity.ok("success");
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<PostListResponseDto>> findAllPost() {
        List<PostListResponseDto> postListResponseDtos = postService.findAll();
        
        return ResponseEntity.ok(postListResponseDtos);
    }
    
    @GetMapping("/search/title")
    public ResponseEntity<List<PostListResponseDto>> findPostByTitle(@RequestParam String title) {
        List<PostListResponseDto> postListResponseDtos = postService.findByTitle(title);
        return ResponseEntity.ok(postListResponseDtos);
    }
    
    @GetMapping("/search/category")
    public ResponseEntity<List<PostListResponseDto>> findPostByCategory(@RequestParam PostCategory category) {
        List<PostListResponseDto> postListResponseDtos = postService.findByCategory(category);
        
        return ResponseEntity.ok(postListResponseDtos);
    }
    
    @GetMapping("search/nickname")
    public ResponseEntity<List<PostListResponseDto>> findPostByNickname(@RequestParam String nickname) {
        
        List<PostListResponseDto> postListResponseDtos = postService.findByNickname(nickname);
        
        return ResponseEntity.ok(postListResponseDtos);
    }
    
    @GetMapping("search/date")
    public ResponseEntity<List<PostListResponseDto>> findPostByCreatedDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdDate) {
        List<PostListResponseDto> postListResponseDtos = postService.findByCreatedDate(createdDate);
        return ResponseEntity.ok(postListResponseDtos);
        
    }
    
    @GetMapping("/search/memberId")
    public ResponseEntity<List<PostListResponseDto>> findPostByMemberId(@RequestParam Long memberId) {
        List<PostListResponseDto> postListResponseDtos = postService.findByMemberId(memberId);
        return ResponseEntity.ok(postListResponseDtos);
    }
    
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> readPost(@PathVariable Long postId) {
        PostResponseDto postResponseDto = postService.readPost(postId);
        return ResponseEntity.ok(postResponseDto);
    }
}
