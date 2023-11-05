package com.example.review.post.controller;

import com.example.review.post.dto.*;
import com.example.review.post.service.PostService;
import com.example.review.post.type.PostCategory;
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
    
    @PostMapping("/new")
    public ResponseEntity<?> createPost(@RequestBody @Valid PostCreateRequestDto postCreateRequestDto) {
        postService.createPost(postCreateRequestDto);
        return ResponseEntity.ok("success");
    }
    
    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody @Valid PostUpdateRequestDto postUpdateRequestDto) {
        postService.updatePost(postId, postUpdateRequestDto);
        return ResponseEntity.ok("success");
    }
    
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId, @RequestBody @Valid PostDeleteRequestDto postDeleteRequestDto) {
        postService.deletePost(postId, postDeleteRequestDto);
        return ResponseEntity.ok("success");
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<PostListResponseDto>> findAllPost() {
        List<PostListResponseDto> listDto = postService.findAll();
        return ResponseEntity.ok(listDto);
    }
    
    @GetMapping("/title")
    public ResponseEntity<List<PostListResponseDto>> findPostByTitle(@RequestParam String title) {
        List<PostListResponseDto> listDto = postService.findByTitle(title);
        return ResponseEntity.ok(listDto);
    }
    
    @GetMapping("/category")
    public ResponseEntity<List<PostListResponseDto>> findPostByCategory(@RequestParam PostCategory category) {
        List<PostListResponseDto> listDto = postService.findByCategory(category);
        return ResponseEntity.ok(listDto);
    }
    
    @GetMapping("/nickname")
    public ResponseEntity<List<PostListResponseDto>> findPostByNickname(@RequestParam String nickname) {
        List<PostListResponseDto> listDto = postService.findByNickname(nickname);
        return ResponseEntity.ok(listDto);
    }
    
    @GetMapping("/date")
    public ResponseEntity<List<PostListResponseDto>> findPostByCreatedDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdDate) {
        List<PostListResponseDto> listDto = postService.findByCreatedDate(createdDate);
        return ResponseEntity.ok(listDto);
    }
    
    @GetMapping("/memberId")
    public ResponseEntity<List<PostListResponseDto>> findPostByMemberId(@RequestParam Long memberId) {
        List<PostListResponseDto> listDto = postService.findByMemberId(memberId);
        return ResponseEntity.ok(listDto);
    }
    
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> readPost(@PathVariable Long postId) {
        PostResponseDto postResponseDto = postService.readPost(postId);
        return ResponseEntity.ok(postResponseDto);
    }
    
    // 회원 페이지에서 몇 개의 게시물을 작성했는지 확인하는 메서드
    @GetMapping("/member/count")
    public ResponseEntity<Long> countPostsByMemberId(@RequestParam Long memberId) {
        Long countPosts = postService.countPostsByMemberId(memberId);
        return ResponseEntity.ok(countPosts);
    }
}
