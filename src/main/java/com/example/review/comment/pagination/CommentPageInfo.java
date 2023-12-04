package com.example.review.comment.pagination;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "CommentPageInfoBuilder")
public class CommentPageInfo {
    private int pageNo;
    private final int pageSize = 10;
    private String sortBy = "commentCreatedDate";
    private boolean isDescending = true;
    
    public static CommentPageInfoBuilder builder(int pageNo) {
        return CommentPageInfoBuilder()
                .pageNo(pageNo);
    }
}
