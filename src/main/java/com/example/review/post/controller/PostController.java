package com.example.review.post.controller;

import com.example.review.post.dto.PostDeleteRequestDto;
import com.example.review.post.dto.PostInputDto;
import com.example.review.post.dto.PostListResponseDto;
import com.example.review.post.dto.PostResponseDto;
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
        List<PostListResponseDto> listDto = postService.findAll();
        return ResponseEntity.ok(listDto);
    }
    
    @GetMapping("/search/title")
    public ResponseEntity<List<PostListResponseDto>> findPostByTitle(@RequestParam String title) {
        List<PostListResponseDto> listDto = postService.findByTitle(title);
        return ResponseEntity.ok(listDto);
    }
    
    @GetMapping("/search/category")
    public ResponseEntity<List<PostListResponseDto>> findPostByCategory(@RequestParam PostCategory category) {
        List<PostListResponseDto> listDto = postService.findByCategory(category);
        return ResponseEntity.ok(listDto);
    }
    
    @GetMapping("search/nickname")
    public ResponseEntity<List<PostListResponseDto>> findPostByNickname(@RequestParam String nickname) {
        List<PostListResponseDto> listDto = postService.findByNickname(nickname);
        return ResponseEntity.ok(listDto);
    }
    
    @GetMapping("search/date")
    public ResponseEntity<List<PostListResponseDto>> findPostByCreatedDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdDate) {
        List<PostListResponseDto> listDto = postService.findByCreatedDate(createdDate);
        return ResponseEntity.ok(listDto);
    }
    
    @GetMapping("/search/memberId")
    public ResponseEntity<List<PostListResponseDto>> findPostByMemberId(@RequestParam Long memberId) {
        List<PostListResponseDto> listDto = postService.findByMemberId(memberId);
        return ResponseEntity.ok(listDto);
    }
    
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> readPost(@PathVariable Long postId) {
        PostResponseDto postResponseDto = postService.readPost(postId);
        return ResponseEntity.ok(postResponseDto);
    }
}
