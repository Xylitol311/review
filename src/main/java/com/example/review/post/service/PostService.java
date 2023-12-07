package com.example.review.post.service;

import com.example.review.comment.pagination.CommentPageResponse;
import com.example.review.comment.service.CommentService;
import com.example.review.config.MsgResponseDto;
import com.example.review.member.domain.Member;
import com.example.review.member.service.MemberService;
import com.example.review.post.domain.Post;
import com.example.review.post.dto.*;
import com.example.review.post.pagination.PostPageInfo;
import com.example.review.post.pagination.PostPageResponse;
import com.example.review.post.repository.PostRepository;
import com.example.review.post.type.PostCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberService memberService;
    private final CommentService commentService;
    
    public PostResponseDto createPost(PostCreateRequestDto postCreateRequestDto, Member member) {
        Post post = Post.builder(postCreateRequestDto, member).build();
        Post savePost = postRepository.save(post);
        
        CommentPageResponse commentList = new CommentPageResponse();
        
        return PostResponseDto.builder(savePost, commentList).build();
    }
    
    @Transactional
    public PostResponseDto updatePost(Long postId, PostUpdateRequestDto postUpdateRequestDto, Member member) {
        
        Post nowPost = memberService.findByPostIdAndMember(postId, member);
        
        nowPost.update(postUpdateRequestDto);
        
        
        CommentPageResponse commentList = loadCommentList(nowPost.getPostId());
        return PostResponseDto.builder(nowPost, commentList).build();
    }
    
    public MsgResponseDto deletePost(Long postId, PostDeleteRequestDto postDeleteRequestDto, Member member) {
        
        
        Post deletePost = memberService.findByPostIdAndMember(postId, member);
        
        postRepository.delete(deletePost);
        
        return new MsgResponseDto("게시글 삭제 성공", HttpStatus.OK.value());
    }
    
    public PostPageResponse findAll(PostPageInfo postPageInfo) {
        // 정렬 기준에 따라 pageable 객체 초기화
        Pageable pageable = createPageable(postPageInfo);
        
        // 찾아온 데이터 Page 객체
        Page<Post> postPage = postRepository.findAll(pageable);
        
        // page 객체에서 post 리스트를 추출하여 responseDtos에 저장
        List<PostListResponseDto> responseDtos = createPostListResponseDto(postPage);
        
        return PostPageResponse.builder(postPageInfo, postPage, responseDtos).build();
    }
    
    public PostPageResponse findByTitle(PostPageInfo postPageInfo, String title) {
        // 정렬 기준에 따라 pageable 객체 초기화
        Pageable pageable = createPageable(postPageInfo);
        
        // 찾아온 데이터 Page 객체
        Page<Post> postPage = postRepository.findAllByTitle(pageable, title);
        
        // page 객체에서 post 리스트를 추출하여 responseDtos에 저장
        List<PostListResponseDto> responseDtos = createPostListResponseDto(postPage);
        
        return PostPageResponse.builder(postPageInfo, postPage, responseDtos).build();
    }
    
    public PostPageResponse findByCategory(PostPageInfo postPageInfo, PostCategory category) {
        // 정렬 기준에 따라 pageable 객체 초기화
        Pageable pageable = createPageable(postPageInfo);
        
        // 찾아온 데이터 Page 객체
        Page<Post> postPage = postRepository.findAllByCategory(pageable, category);
        
        // page 객체에서 post 리스트를 추출하여 responseDtos에 저장
        List<PostListResponseDto> responseDtos = createPostListResponseDto(postPage);
        
        return PostPageResponse.builder(postPageInfo, postPage, responseDtos).build();
    }
    
    public PostPageResponse findByNickname(PostPageInfo postPageInfo, String nickname) {
        // 정렬 기준에 따라 pageable 객체 초기화
        Pageable pageable = createPageable(postPageInfo);
        
        // 찾아온 데이터 Page 객체
        Page<Post> postPage = postRepository.findAllByMember_Nickname(pageable, nickname);
        
        // page 객체에서 post 리스트를 추출하여 responseDtos에 저장
        List<PostListResponseDto> responseDtos = createPostListResponseDto(postPage);
        
        return PostPageResponse.builder(postPageInfo, postPage, responseDtos).build();
    }
    
    public PostPageResponse findByCreatedDate(PostPageInfo postPageInfo, LocalDate createdDate) {
        // 정렬 기준에 따라 pageable 객체 초기화
        Pageable pageable = createPageable(postPageInfo);
        
        // 찾아온 데이터 Page 객체
        Page<Post> postPage = postRepository.findAllByPostCreatedDate(pageable, createdDate);
        
        // page 객체에서 post 리스트를 추출하여 responseDtos에 저장
        List<PostListResponseDto> responseDtos = createPostListResponseDto(postPage);
        
        return PostPageResponse.builder(postPageInfo, postPage, responseDtos).build();
    }
    
    // 수정 꼭 필요!!!
    public PostPageResponse findByMemberId(PostPageInfo postPageInfo, Long memberId) {
        // 정렬 기준에 따라 pageable 객체 초기화
        Pageable pageable = createPageable(postPageInfo);

        // 찾아온 데이터 Page 객체
        Page<Post> postPage = postRepository.findAllByMember_MemberId(pageable, memberId);

        // page 객체에서 post 리스트를 추출하여 responseDtos에 저장
        List<PostListResponseDto> responseDtos = createPostListResponseDto(postPage);

        return PostPageResponse.builder(postPageInfo, postPage, responseDtos).build();
    }
    
    
    public PostResponseDto readPost(Long postId) {
        if (postRepository.findById(postId).isEmpty()) {
            return null;
        }
        Post nowPost = postRepository.findById(postId).get();
        
        CommentPageResponse commentList = loadCommentList(postId);
        
        return PostResponseDto.builder(nowPost, commentList).build();
    }
    
    public Long countPostsByMemberId(Long memberId) {
        return postRepository.countByMember_MemberId(memberId);
    }
    
    private Pageable createPageable(PostPageInfo postPageInfo) {
        Pageable pageable;
        if (postPageInfo.isDescending()) {
            pageable = PageRequest.of(postPageInfo.getPageNo(), postPageInfo.getPageSize(), Sort.by(postPageInfo.getSortBy()).descending());
        } else {
            pageable = PageRequest.of(postPageInfo.getPageNo(), postPageInfo.getPageSize(), Sort.by(postPageInfo.getSortBy()).ascending());
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
    
    private CommentPageResponse loadCommentList(Long postId){
        CommentPageResponse commentList = commentService.readComments(0, postId);
        return commentList;
    }
}
