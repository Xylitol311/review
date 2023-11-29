package com.example.review.post.controller;

import com.example.review.pagination.PageInfo;
import com.example.review.pagination.PageResponse;
import com.example.review.pagination.PostSortBy;
import com.example.review.post.dto.PostCreateRequestDto;
import com.example.review.post.dto.PostDeleteRequestDto;
import com.example.review.post.dto.PostResponseDto;
import com.example.review.post.dto.PostUpdateRequestDto;
import com.example.review.post.service.PostService;
import com.example.review.post.type.PostCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    public ResponseEntity<PageResponse> findAllPost(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "POST_CREATED_DATE", required = false) PostSortBy sortBy,
            @RequestParam(value = "isDescending", defaultValue = "true") boolean isDescending
    ) {
        PageInfo pageInfo = PageInfo.builder(pageNo, pageSize, sortBy, isDescending).build();
        
        PageResponse pageResponse = postService.findAll(pageInfo);
        return ResponseEntity.ok(pageResponse);
    }
    
    @GetMapping("/title")
    public ResponseEntity<PageResponse> findPostByTitle(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "POST_CREATED_DATE", required = false) PostSortBy sortBy,
            @RequestParam(value = "isDescending", defaultValue = "true") boolean isDescending,
            @RequestParam String title
    ) {
        PageInfo pageInfo = PageInfo.builder(pageNo, pageSize, sortBy, isDescending).build();
        
        PageResponse pageResponse = postService.findByTitle(pageInfo, title);
        return ResponseEntity.ok(pageResponse);
    }
    
    @GetMapping("/category")
    public ResponseEntity<PageResponse> findPostByCategory(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "POST_CREATED_DATE", required = false) PostSortBy sortBy,
            @RequestParam(value = "isDescending", defaultValue = "true") boolean isDescending,
            @RequestParam PostCategory postCategory
    ) {
        PageInfo pageInfo = PageInfo.builder(pageNo, pageSize, sortBy, isDescending).build();
        
        PageResponse pageResponse = postService.findByCategory(pageInfo, postCategory);
        return ResponseEntity.ok(pageResponse);
    }
    
    @GetMapping("/nickname")
    public ResponseEntity<PageResponse> findPostByNickname(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "POST_CREATED_DATE", required = false) PostSortBy sortBy,
            @RequestParam(value = "isDescending", defaultValue = "true") boolean isDescending,
            @RequestParam String nickname
    ) {
        PageInfo pageInfo = PageInfo.builder(pageNo, pageSize, sortBy, isDescending).build();
        
        PageResponse pageResponse = postService.findByNickname(pageInfo, nickname);
        return ResponseEntity.ok(pageResponse);
    }
    
    @GetMapping("/date")
    public ResponseEntity<PageResponse> findPostByCreatedDate(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "POST_CREATED_DATE", required = false) PostSortBy sortBy,
            @RequestParam(value = "isDescending", defaultValue = "true") boolean isDescending,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdDate
    ) {
        PageInfo pageInfo = PageInfo.builder(pageNo, pageSize, sortBy, isDescending).build();
        PageResponse pageResponse = postService.findByCreatedDate(pageInfo, createdDate);
        return ResponseEntity.ok(pageResponse);
    }
    
    @GetMapping("/memberId")
    public ResponseEntity<PageResponse> findPostByMemberId(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "POST_CREATED_DATE", required = false) PostSortBy sortBy,
            @RequestParam(value = "isDescending", defaultValue = "true") boolean isDescending,
            @RequestParam Long memberId
    ) {
        PageInfo pageInfo = PageInfo.builder(pageNo, pageSize, sortBy, isDescending).build();
        
        PageResponse pageResponse = postService.findByMemberId(pageInfo, memberId);
        return ResponseEntity.ok(pageResponse);
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
