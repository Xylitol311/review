package com.example.review.post.pagination;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "PostPageInfoBuilder")
public class PostPageInfo {
    private int pageNo;
    private final int pageSize = 10;
    private String sortBy;
    private boolean isDescending;
    
    public static PostPageInfoBuilder builder(int pageNo, PostSortBy postSortBy, boolean isDescending) {
        return PostPageInfoBuilder()
                .pageNo(pageNo)
                .sortBy(postSortBy.getSortBy())
                .isDescending(isDescending);
    }
}
