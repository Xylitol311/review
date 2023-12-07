package com.example.review.comment.pagination;

import com.example.review.comment.domain.Comment;
import com.example.review.comment.dto.CommentResponseDto;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "CommentPageResponseBuilder")
public class CommentPageResponse { // 추후 댓글 파트 페이징 처리에도 사용하기 위해 별도 패키지에 생성
    private List<CommentResponseDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
    
    public static CommentPageResponseBuilder builder(int pageNo, Page<Comment> page, List<CommentResponseDto> responseDtos) {
        return CommentPageResponseBuilder()
                .content(responseDtos)
                .pageNo(pageNo)
                .pageSize(10)
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast());
    }
}
