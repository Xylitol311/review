package com.example.review.post.service;

import com.example.review.exception.ErrorCode;
import com.example.review.exception.MemberNotFoundException;
import com.example.review.exception.PostNotFoundException;
import com.example.review.member.domain.Member;
import com.example.review.pagination.PageInfo;
import com.example.review.pagination.PageResponse;
import com.example.review.post.domain.Post;
import com.example.review.post.dto.*;
import com.example.review.member.repository.MemberRepository;
import com.example.review.post.repository.PostRepository;
import com.example.review.post.type.PostCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    
    public void createPost(PostCreateRequestDto postCreateRequestDto) {
        Member nowMember = memberRepository.findById(postCreateRequestDto.getMemberId()).orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND, ErrorCode.MEMBER_NOT_FOUND.getMessage()));
        
        Post post = Post.builder()
                .title(postCreateRequestDto.getTitle())
                .category(postCreateRequestDto.getCategory())
                .text(postCreateRequestDto.getText())
                .member(nowMember)
                .postCommentCount(0L)
                .build();
        
        postRepository.save(post);
    }
    
    @Transactional
    public void updatePost(Long postId, PostUpdateRequestDto postUpdateRequestDto) {
        Post nowPost = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        
        // 권한이 있는지 확인 (로그인 기능 구현 후 추가 예정)
        if (!nowPost.getMember().getMemberId().equals(postUpdateRequestDto.getMemberId())) {
            // 예외처리
        }
        
        nowPost.setTitle(postUpdateRequestDto.getTitle());
        nowPost.setCategory(postUpdateRequestDto.getCategory());
        nowPost.setText(postUpdateRequestDto.getText());
    }
    
    public void deletePost(Long postId, PostDeleteRequestDto postDeleteRequestDto) {
        Post deletePost = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        
        // 권한이 있는지 확인 (로그인 기능 구현 후 추가 예정)
        if (!deletePost.getMember().getMemberId().equals(postDeleteRequestDto.getMemberId())) {
            //예외처리
        }
        
        postRepository.delete(deletePost);
    }
    
    public PageResponse findAll(PageInfo pageInfo) {
        // 정렬 기준에 따라 pageable 객체 초기화
        Pageable pageable = createPageable(pageInfo);
        
        // 찾아온 데이터 Page 객체
        Page<Post> postPage = postRepository.findAll(pageable);
        
        // page 객체에서 post 리스트를 추출하여 responseDtos에 저장
        List<PostListResponseDto> responseDtos = createPostListResponseDto(postPage);
        
        return PageResponse.builder(pageInfo, postPage, responseDtos).build();
    }
    
    public PageResponse findByTitle(PageInfo pageInfo, String title) {
        // 정렬 기준에 따라 pageable 객체 초기화
        Pageable pageable = createPageable(pageInfo);
        
        // 찾아온 데이터 Page 객체
        Page<Post> postPage = postRepository.findAllByTitle(pageable, title);
        
        // page 객체에서 post 리스트를 추출하여 responseDtos에 저장
        List<PostListResponseDto> responseDtos = createPostListResponseDto(postPage);
        
        return PageResponse.builder(pageInfo, postPage, responseDtos).build();
    }
    
    public PageResponse findByCategory(PageInfo pageInfo, PostCategory category) {
        // 정렬 기준에 따라 pageable 객체 초기화
        Pageable pageable = createPageable(pageInfo);
        
        // 찾아온 데이터 Page 객체
        Page<Post> postPage = postRepository.findAllByCategory(pageable, category);
        
        // page 객체에서 post 리스트를 추출하여 responseDtos에 저장
        List<PostListResponseDto> responseDtos = createPostListResponseDto(postPage);
        
        return PageResponse.builder(pageInfo, postPage, responseDtos).build();
    }
    
    public PageResponse findByNickname(PageInfo pageInfo, String nickname) {
        // 정렬 기준에 따라 pageable 객체 초기화
        Pageable pageable = createPageable(pageInfo);
        
        // 찾아온 데이터 Page 객체
        Page<Post> postPage = postRepository.findAllByMember_Nickname(pageable, nickname);
        
        // page 객체에서 post 리스트를 추출하여 responseDtos에 저장
        List<PostListResponseDto> responseDtos = createPostListResponseDto(postPage);
        
        return PageResponse.builder(pageInfo, postPage, responseDtos).build();
    }
    
    public PageResponse findByCreatedDate(PageInfo pageInfo, LocalDate createdDate) {
        // 정렬 기준에 따라 pageable 객체 초기화
        Pageable pageable = createPageable(pageInfo);
        
        // 찾아온 데이터 Page 객체
        Page<Post> postPage = postRepository.findAllByPostCreatedDate(pageable, createdDate);
        
        // page 객체에서 post 리스트를 추출하여 responseDtos에 저장
        List<PostListResponseDto> responseDtos = createPostListResponseDto(postPage);
        
        return PageResponse.builder(pageInfo, postPage, responseDtos).build();
    }
    
    public PageResponse findByMemberId(PageInfo pageInfo, Long memberId) {
        // 정렬 기준에 따라 pageable 객체 초기화
        Pageable pageable = createPageable(pageInfo);
        
        // 찾아온 데이터 Page 객체
        Page<Post> postPage = postRepository.findAllByMember_MemberId(pageable, memberId);
        
        // page 객체에서 post 리스트를 추출하여 responseDtos에 저장
        List<PostListResponseDto> responseDtos = createPostListResponseDto(postPage);
        
        return PageResponse.builder(pageInfo, postPage, responseDtos).build();
    }
    
    
    public PostResponseDto readPost(Long postId) {
        if (postRepository.findById(postId).isEmpty()) {
            return null;
        }
        Post nowPost = postRepository.findById(postId).get();
        
        return PostResponseDto.builder(nowPost).build();
    }
    
    public Long countPostsByMemberId(Long memberId) {
        return postRepository.countByMember_MemberId(memberId);
    }
    
    private Pageable createPageable(PageInfo pageInfo) {
        Pageable pageable;
        if (pageInfo.isDescending()) {
            pageable = PageRequest.of(pageInfo.getPageNo(), pageInfo.getPageSize(), Sort.by(pageInfo.getSortBy()).descending());
        } else {
            pageable = PageRequest.of(pageInfo.getPageNo(), pageInfo.getPageSize(), Sort.by(pageInfo.getSortBy()).ascending());
        }
        return pageable;
    }
    
    private List<PostListResponseDto> createPostListResponseDto(Page<Post> postPage) {
        List<Post> posts = postPage.getContent();
        List<PostListResponseDto> responseDtos = new ArrayList<>();
        for (Post post : posts) {
            PostListResponseDto findPost = PostListResponseDto.builder(post).build();
            responseDtos.add(findPost);
        }
        
        return responseDtos;
    }
}
