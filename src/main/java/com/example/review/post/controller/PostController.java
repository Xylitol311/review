package com.example.review.post.controller;

import com.example.review.config.security.UserDetailsImpl;
import com.example.review.post.dto.PostCreateRequestDto;
import com.example.review.post.dto.PostDeleteRequestDto;
import com.example.review.post.dto.PostResponseDto;
import com.example.review.post.dto.PostUpdateRequestDto;
import com.example.review.post.pagination.PostPageInfo;
import com.example.review.post.pagination.PostPageResponse;
import com.example.review.post.pagination.PostSortBy;
import com.example.review.post.service.PostService;
import com.example.review.post.type.PostCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    
    @PostMapping("/new")
    public ResponseEntity<?> createPost(@RequestBody @Valid PostCreateRequestDto postCreateRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDto postResponseDto = postService.createPost(postCreateRequestDto, userDetails.getUser());
        return ResponseEntity.ok(postResponseDto);
    }
    
    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody @Valid PostUpdateRequestDto postUpdateRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDto postResponseDto = postService.updatePost(postId, postUpdateRequestDto, userDetails.getUser());
        return ResponseEntity.ok(postResponseDto);
    }
    
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId, PostDeleteRequestDto postDeleteRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(postId, postDeleteRequestDto, userDetails.getUser());
        return ResponseEntity.ok("success");
    }
    
    @GetMapping("/all")
    public ResponseEntity<PostPageResponse> findAllPost(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "sortBy", defaultValue = "POST_CREATED_DATE", required = false) PostSortBy sortBy,
            @RequestParam(value = "isDescending", defaultValue = "true") boolean isDescending
    ) {
        PostPageInfo postPageInfo = PostPageInfo.builder(pageNo, sortBy, isDescending).build();
        
        PostPageResponse postPageResponse = postService.findAll(postPageInfo);
        return ResponseEntity.ok(postPageResponse);
    }
    
    @GetMapping("/title")
    public ResponseEntity<PostPageResponse> findPostByTitle(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "sortBy", defaultValue = "POST_CREATED_DATE", required = false) PostSortBy sortBy,
            @RequestParam(value = "isDescending", defaultValue = "true") boolean isDescending,
            @RequestParam String title
    ) {
        PostPageInfo postPageInfo = PostPageInfo.builder(pageNo, sortBy, isDescending).build();
        
        PostPageResponse postPageResponse = postService.findByTitle(postPageInfo, title);
        return ResponseEntity.ok(postPageResponse);
    }
    
    @GetMapping("/category")
    public ResponseEntity<PostPageResponse> findPostByCategory(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "sortBy", defaultValue = "POST_CREATED_DATE", required = false) PostSortBy sortBy,
            @RequestParam(value = "isDescending", defaultValue = "true") boolean isDescending,
            @RequestParam PostCategory postCategory
    ) {
        PostPageInfo postPageInfo = PostPageInfo.builder(pageNo, sortBy, isDescending).build();
        
        PostPageResponse postPageResponse = postService.findByCategory(postPageInfo, postCategory);
        return ResponseEntity.ok(postPageResponse);
    }
    
    @GetMapping("/nickname")
    public ResponseEntity<PostPageResponse> findPostByNickname(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "sortBy", defaultValue = "POST_CREATED_DATE", required = false) PostSortBy sortBy,
            @RequestParam(value = "isDescending", defaultValue = "true") boolean isDescending,
            @RequestParam String nickname
    ) {
        PostPageInfo postPageInfo = PostPageInfo.builder(pageNo, sortBy, isDescending).build();
        
        PostPageResponse postPageResponse = postService.findByNickname(postPageInfo, nickname);
        return ResponseEntity.ok(postPageResponse);
    }
    
    @GetMapping("/date")
    public ResponseEntity<PostPageResponse> findPostByCreatedDate(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "sortBy", defaultValue = "POST_CREATED_DATE", required = false) PostSortBy sortBy,
            @RequestParam(value = "isDescending", defaultValue = "true") boolean isDescending,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdDate
    ) {
        PostPageInfo postPageInfo = PostPageInfo.builder(pageNo, sortBy, isDescending).build();
        PostPageResponse postPageResponse = postService.findByCreatedDate(postPageInfo, createdDate);
        return ResponseEntity.ok(postPageResponse);
    }
    
    @GetMapping("/memberId")
    public ResponseEntity<PostPageResponse> findPostByMemberId( // 회원 파트로 이동 고려
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "sortBy", defaultValue = "POST_CREATED_DATE", required = false) PostSortBy sortBy,
            @RequestParam(value = "isDescending", defaultValue = "true") boolean isDescending,
            @RequestParam Long memberId
    ) {
        PostPageInfo postPageInfo = PostPageInfo.builder(pageNo, sortBy, isDescending).build();

        PostPageResponse postPageResponse = postService.findByMemberId(postPageInfo, memberId);
        return ResponseEntity.ok(postPageResponse);
    }
    
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> readPost(
            @PathVariable Long postId
    ) {
        PostResponseDto postResponseDto = postService.readPost(postId);
        return ResponseEntity.ok(postResponseDto);
    }
    
    // 회원 페이지에서 몇 개의 게시물을 작성했는지 확인하는 메서드
    @GetMapping("/member/count") // 회원 파트로 이동 고려
    public ResponseEntity<Long> countPostsByMemberId(@RequestParam Long memberId) {
        Long countPosts = postService.countPostsByMemberId(memberId);
        return ResponseEntity.ok(countPosts);
    }
}
