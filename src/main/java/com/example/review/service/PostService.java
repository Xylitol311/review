package com.example.review.service;

import com.example.review.domain.Member;
import com.example.review.domain.Post;
import com.example.review.dto.PostCreatRequestDto;
import com.example.review.dto.PostDeleteRequestDto;
import com.example.review.dto.PostListResponseDto;
import com.example.review.dto.PostUpdateRequestDto;
import com.example.review.repository.MemberRepository;
import com.example.review.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    
    public String createPost(PostCreatRequestDto postCreatRequestDto) {
        
        // 멤버 객체 입력
        if (memberRepository.findById(postCreatRequestDto.getMemberId()).isEmpty()) {
            return "해당 유저는 없습니다.";
        }
        Member nowMember = memberRepository.findById(postCreatRequestDto.getMemberId()).get();
        
        Post post = Post.builder()
                .title(postCreatRequestDto.getTitle())
                .category(postCreatRequestDto.getCategory())
                .text(postCreatRequestDto.getText())
                .member(nowMember)
                .postCommentCount(0L)
                .build();
        
        postRepository.save(post);
        return "success";
    }
    
    public String updatePost(Long postId, PostUpdateRequestDto postUpdateRequestDto) {
        // Post가 있는지 확인
        if (postRepository.findById(postId).isEmpty()) {
            return "해당 포스트가 없습니다.";
        }

        Post nowPost = postRepository.findById(postId).get();
        // 권한이 있는지 확인
        if (!nowPost.getMember().getMemberId().equals(postUpdateRequestDto.getMemberId())) {
            return "수정 권한이 없습니다.";
        }
        
        nowPost.setTitle(postUpdateRequestDto.getTitle());
        nowPost.setCategory(postUpdateRequestDto.getCategory());
        nowPost.setText(postUpdateRequestDto.getText());
        
        postRepository.save(nowPost);
        return "success";
    }
    
    public String deletePost(Long postId, PostDeleteRequestDto postDeleteRequestDto) {
        // Post가 있는지 확인
        if (postRepository.findById(postId).isEmpty()) {
            return "해당 포스트가 없습니다.";
        }
        Post deletePost = postRepository.findById(postId).get();
        
        // 권한이 있는지 확인
        if (!deletePost.getMember().getMemberId().equals(postDeleteRequestDto.getMemberId())) {
            return "삭제 권한이 없습니다.";
        }
        
        postRepository.delete(deletePost);
        return "success";
    }
    
    public List<PostListResponseDto> findAll() {
        List<Post> posts = postRepository.findAll();
        List<PostListResponseDto> findPosts = new ArrayList<>();
        
        for (Post post : posts) {
            PostListResponseDto findPost = PostListResponseDto.builder()
                    .postId(post.getPostId())
                    .title(post.getTitle())
                    .category(post.getCategory())
                    .postCreatedDate(post.getPostCreatedDate())
                    .postCommentCount(post.getPostCommentCount())
                    .memberId(post.getMember().getMemberId())
                    .build();
            
            findPosts.add(findPost);
        }
        
        return findPosts;
    }
}
