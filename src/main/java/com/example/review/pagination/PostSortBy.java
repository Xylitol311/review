package com.example.review.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PostSortBy {
    POST_CREATED_DATE("postCreatedDate"),
    POST_COMMENT_COUNT("postCommentCount");
    
    private String sortBy;
}
