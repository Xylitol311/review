package com.example.review.service;

import com.example.review.domain.Post;
import com.example.review.dto.PostCreatRequestDto;
import com.example.review.repository.PostRepository;
import com.example.review.type.PostCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    
    public String createPost(PostCreatRequestDto postCreatRequestDto) {
        Post post = new Post();
        PostCategory postCategory = PostCategory.valueOf(postCreatRequestDto.getCategory());
        post.setTitle(postCreatRequestDto.getTitle());
        post.setCategory(postCategory);
        post.setText(postCreatRequestDto.getText());
        post.setPostCommentCount(0L);
        post.setMember(postCreatRequestDto.getMember());
        postRepository.save(post);
        return "success";
    }
}
