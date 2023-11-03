package com.example.review.post.service;

import com.example.review.exception.ErrorCode;
import com.example.review.exception.PostNotFoundException;
import com.example.review.post.domain.Member;
import com.example.review.post.domain.Post;
import com.example.review.post.dto.PostDeleteRequestDto;
import com.example.review.post.dto.PostInputDto;
import com.example.review.post.dto.PostListResponseDto;
import com.example.review.post.dto.PostResponseDto;
import com.example.review.post.repository.MemberRepository;
import com.example.review.post.repository.PostRepository;
import com.example.review.post.type.PostCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    
    public void createPost(PostInputDto postInputDto) {
        
        // 멤버 객체 입력
        if (memberRepository.findById(postInputDto.getMemberId()).isEmpty()) {
            //예외처리
        }
        
        Member nowMember = memberRepository.findById(postInputDto.getMemberId()).get();
        
        Post post = Post.builder()
                .title(postInputDto.getTitle())
                .category(postInputDto.getCategory())
                .text(postInputDto.getText())
                .member(nowMember)
                .postCommentCount(0L)
                .build();
        
        postRepository.save(post);
    }
    
    public void updatePost(Long postId, PostInputDto postInputDto) {
        Post nowPost = postRepository.findById(postId).orElseThrow(()->new PostNotFoundException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        
        // 권한이 있는지 확인 (로그인 기능 구현 후 추가 예정)
        if (!nowPost.getMember().getMemberId().equals(postInputDto.getMemberId())) {
            // 예외처리
        }
        
        nowPost.setTitle(postInputDto.getTitle());
        nowPost.setCategory(postInputDto.getCategory());
        nowPost.setText(postInputDto.getText());
        
        postRepository.save(nowPost);
    }
    
    public void deletePost(Long postId, PostDeleteRequestDto postDeleteRequestDto) {
        Post deletePost = postRepository.findById(postId).orElseThrow(()->new PostNotFoundException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        
        // 권한이 있는지 확인 (로그인 기능 구현 후 추가 예정)
        if (!deletePost.getMember().getMemberId().equals(postDeleteRequestDto.getMemberId())) {
            //예외처리
        }
        
        postRepository.delete(deletePost);
    }
    
    public List<PostListResponseDto> findAll() {
        List<Post> posts = postRepository.findAll();
        
        List<PostListResponseDto> findPosts = new ArrayList<>();
        
        for (Post post : posts) {
            PostListResponseDto findPost = PostListResponseDto.builder(post).build();
            findPosts.add(findPost);
        }
        
        return findPosts;
    }
    public List<PostListResponseDto> findByTitle(String title) {
        List<Post> posts = postRepository.findAllByTitle(title);
        
        List<PostListResponseDto> findPosts = new ArrayList<>();
        
        for (Post post : posts) {
            PostListResponseDto findPost = PostListResponseDto.builder(post).build();
            findPosts.add(findPost);
        }
        
        return findPosts;
    }
    
    public List<PostListResponseDto> findByCategory(PostCategory category) {
        
        List<Post> posts = postRepository.findAllByCategory(category);
        
        List<PostListResponseDto> findPosts = new ArrayList<>();
        
        for (Post post : posts) {
            PostListResponseDto findPost = PostListResponseDto.builder(post).build();
            findPosts.add(findPost);
        }
        
        return findPosts;
    }
    
    public List<PostListResponseDto> findByNickname(String nickname) {
        List<Post> posts = postRepository.findAllByMember_Nickname(nickname);
        
        List<PostListResponseDto> findPosts = new ArrayList<>();
        
        for (Post post : posts) {
            PostListResponseDto findPost = PostListResponseDto.builder(post).build();
            findPosts.add(findPost);
        }
        
        return findPosts;
    }
    
    public List<PostListResponseDto> findByCreatedDate(LocalDate createdDate) {
        List<Post> posts = postRepository.findAllByPostCreatedDate(createdDate);
        
        List<PostListResponseDto> findPosts = new ArrayList<>();
        
        for (Post post : posts) {
            PostListResponseDto findPost = PostListResponseDto.builder(post).build();
            
            findPosts.add(findPost);
        }
        
        return findPosts;
    }
    
    public List<PostListResponseDto> findByMemberId(Long memberId) {
        List<Post> posts = postRepository.findAllByMember_MemberId(memberId);
        List<PostListResponseDto> findPosts = new ArrayList<>();
        
        for (Post post : posts) {
            PostListResponseDto findPost = PostListResponseDto.builder(post).build();
            findPosts.add(findPost);
        }
        
        return findPosts;
    }
    
    public PostResponseDto readPost(Long postId) {
        if (postRepository.findById(postId).isEmpty()) {
            return null;
        }
        Post nowPost = postRepository.findById(postId).get();
        PostResponseDto postResponseDto = PostResponseDto.builder(nowPost).build();

        return postResponseDto;
    }
}
