package com.example.review.post.pagination;

import com.example.review.post.domain.Post;
import com.example.review.post.dto.PostListResponseDto;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "PostPageResponseBuilder")
public class PostPageResponse { // 추후 댓글 파트 페이징 처리에도 사용하기 위해 별도 패키지에 생성
    private List<PostListResponseDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
    
    public static PostPageResponseBuilder builder(PostPageInfo postPageInfo, Page<Post> page, List<PostListResponseDto> responseDtos) {
        return PostPageResponseBuilder()
                .content(responseDtos)
                .pageNo(postPageInfo.getPageNo())
                .pageSize(postPageInfo.getPageSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast());
    }
}
