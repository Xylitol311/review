package com.example.review.pagination;

import com.example.review.post.dto.PostListResponseDto;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "PageResponseBuilder")
public class PageResponse { // 추후 댓글 파트 페이징 처리에도 사용하기 위해 별도 패키지에 생성
    private List<?> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
    
    public static PageResponseBuilder builder(PageInfo pageInfo, Page<?> page, List<?> responseDtos) {
        return PageResponseBuilder()
                .content(responseDtos)
                .pageNo(pageInfo.getPageNo())
                .pageSize(pageInfo.getPageSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast());
    }
}
