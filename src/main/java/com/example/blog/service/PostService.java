package com.example.blog.service;

import com.example.blog.entity.Post;
import com.example.blog.entity.dto.PostDto;
import com.example.blog.entity.dto.PostResponse;

import java.util.List;

public interface PostService {
    Post createPost(PostDto postDto);

    PostResponse findAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    Post findPostById(Long id);

    PostDto updatePost(PostDto postDto, Long id);

    void deletePostById(Long id);
}
