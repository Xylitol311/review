package com.example.review.comment.service;

import com.example.review.comment.domain.Comment;
import com.example.review.comment.dto.CommentRequestDto;
import com.example.review.comment.dto.CommentResponseDto;
import com.example.review.comment.pagination.CommentPageResponse;
import com.example.review.comment.repository.CommentRepository;
import com.example.review.config.MsgResponseDto;
import com.example.review.exception.CustomException;
import com.example.review.member.domain.Member;
import com.example.review.member.service.MemberService;
import com.example.review.post.domain.Post;
import com.example.review.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.example.review.exception.ErrorCode.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CommentService {
  private final PostRepository postRepository;
  private final CommentRepository commentRepository;
  private final MemberService memberService;
  
  public CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto, Member member) {
    Post post = postRepository.findById(postId).orElseThrow(
            () -> new CustomException(POST_NOT_FOUND)
    );
    
    Comment comment = Comment.builder(commentRequestDto, post, member).build();
    Comment saveComment = commentRepository.save(comment);
    
    return CommentResponseDto.builder(saveComment).build();
  }
  
  @Transactional
  public CommentResponseDto updateComment(Long postId, Long commentId, CommentRequestDto commentRequestDto, Member member) {
    
    // 게시글이 있는지
    Post post = postRepository.findById(postId).orElseThrow(
            () -> new CustomException(POST_NOT_FOUND)
    );
    
    Comment comment = memberService.findByCommentIdAndMember(commentId, member);
    
    comment.update(commentRequestDto);
    
    return CommentResponseDto.builder(comment).build();
  }
  
  // 댓글 삭제
  public MsgResponseDto deleteComment(Long postId, Long commentId, Member member) {
    
    // 게시글이 있는지
    Post post = postRepository.findById(postId).orElseThrow(
            () -> new CustomException(POST_NOT_FOUND)
    );
    
    Comment comment = memberService.findByCommentIdAndMember(commentId, member);
    
    commentRepository.deleteById(commentId);
    
    return new MsgResponseDto("댓글을 삭제했습니다.", HttpStatus.OK.value());
  }
  
  public CommentPageResponse readComments(int pageNo, Long postId) {
    // 정렬 기준에 따라 pageable 객체 초기화
    Pageable pageable = PageRequest.of(pageNo, 10, Sort.by("commentCreatedDate").descending());
    
    // 찾아온 데이터 Page 객체
    Page<Comment> commentPage = commentRepository.findAllByPost_PostId(pageable, postId);
    
    // page 객체에서 post 리스트를 추출하여 responseDtos에 저장
    List<CommentResponseDto> responseDtos = createCommentsResponseDto(commentPage);
    
    return CommentPageResponse.builder(pageNo, commentPage, responseDtos).build();
  }
  
  private List<CommentResponseDto> createCommentsResponseDto(Page<Comment> commentsPage) {
    List<Comment> comments = commentsPage.getContent();
    List<CommentResponseDto> responseDtos = new ArrayList<>();
    for (Comment comment : comments) {
      CommentResponseDto commentResponseDto = CommentResponseDto.builder(comment).build();
      responseDtos.add(commentResponseDto);
    }
    
    return responseDtos;
  }
}
