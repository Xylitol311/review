package com.example.review.comment.service;

import com.example.review.comment.domain.Comment;
import com.example.review.comment.dto.CommentRequestDto;
import com.example.review.comment.dto.CommentResponseDto;
import com.example.review.comment.repository.CommentRepository;
import com.example.review.exception.CustomException;
import com.example.review.exception.ErrorCode;
import com.example.review.member.domain.Member;
import com.example.review.member.domain.Role;
import com.example.review.member.repository.MemberRepository;
import com.example.review.post.domain.Post;
import com.example.review.post.repository.PostRepository;
import com.example.review.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import static com.example.review.exception.ErrorCode.COMMENT_NOT_FOUND;
import static com.example.review.exception.ErrorCode.POST_NOT_FOUND;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;
    
    public CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );
        
        Member member = jwtUtil.getUserInfoFromToken(request);
        Comment comment = Comment.builder(commentRequestDto, post, member).build();
        Comment saveComment = commentRepository.save(comment);
        
        return CommentResponseDto.builder(saveComment).build();
    }
    
    @Transactional
    public CommentResponseDto updateComment(Long boardId, Long commentId, CommentRequestDto commentRequestDto, Member member) {
        
        // 게시글이 있는지
        Post post = postRepository.findById(boardId).orElseThrow (
                () -> new CustomException(POST_NOT_FOUND)
        );
        
        Comment comment = member;
        
        // 사용자의 권한 확인
        if (member.getRole().equals(Role.ADMIN)) {
            comment = commentRepository.findById(commentId).orElseThrow(
                    () -> new CustomException(COMMENT_NOT_FOUND)
            );
        } else {
            comment = commentRepository.findByCommentIdAndMember_MemberId(commentId, member.getMemberId()).orElseThrow (
                    () -> new CustomException(COMMENT_NOT_FOUND)
            );
        }
        
        // username 일치 여부 확인
        if (commentRequestDto.getUsername().equals(member.getLoginId())) {
            comment.update(commentRequestDto);
        } else {
            throw new CustomException(ErrorCode.AUTHORIZATION);
        }
        
        return CommentResponseDto.builder(comment).build();
    }
    
    // 댓글 삭제
    public void deleteComment(Long postId, Long cmtId, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        // 해당 사용자가 토큰을 가진 사용자인지 확인
        Member member = jwtUtil.getUserInfoFromToken(request);
        
        // 게시글이 있는지
        Post post = postRepository.findById(postId).orElseThrow (
                () -> new CustomException(POST_NOT_FOUND)
        );
        
        Comment comment;
        
        // 토큰을 가진 사용자가 '관리자'라면
        if (member.getRole().equals(Role.ADMIN)) {
            comment = commentRepository.findById(cmtId).orElseThrow (      // 관리자는 모든 댓글을 삭제할 권한 있으므로, userId 와 일치여부까지 비교할 필요는 없다
                    () -> new CustomException(COMMENT_NOT_FOUND)
            );
        } else {
            comment = commentRepository.findByCommentIdAndMember_MemberId(cmtId, member.getMemberId()).orElseThrow (
                    () -> new CustomException(COMMENT_NOT_FOUND)
            );
        }
        
        if (commentRequestDto.getUsername().equals(member.getLoginId())) {
            commentRepository.deleteById(cmtId);
        } else {
            throw new CustomException(ErrorCode.AUTHORIZATION);
        }
        
    }
}
