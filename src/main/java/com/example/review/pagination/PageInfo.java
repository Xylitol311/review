package com.example.review.pagination;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "PageInfoBuilder")
public class PageInfo {
    private int pageNo;
    private int pageSize;
    private String sortBy;
    private boolean isDescending;
    
    public static PageInfoBuilder builder(int pageNo, int pageSize, PostSortBy postSortBy, boolean isDescending) {
        return PageInfoBuilder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .sortBy(postSortBy.getSortBy())
                .isDescending(isDescending);
    }
}
