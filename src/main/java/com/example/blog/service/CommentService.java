package com.example.blog.service;

import com.example.blog.entity.dto.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> findCommentsByPostId(long postId);

    CommentDto findCommentById(long postId, long commentId);

    CommentDto updateCommentById(Long postId, Long commentId, CommentDto commentRequest);

    void deleteCommentById(long postId, long commentId);
}
