package com.example.blog.service.impl;

import com.example.blog.entity.Comment;
import com.example.blog.entity.Post;
import com.example.blog.entity.dto.CommentDto;
import com.example.blog.exception.BlogApiException;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.mapper.CommentMapper;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = commentMapper.mapToEntity(commentDto);
        Post post =  postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));
        comment.setPost(post);
        Comment newComment =  commentRepository.save(comment);

        return commentMapper.mapToDto(newComment);
    }

    @Override
    public List<CommentDto> findCommentsByPostId(long postId) {

        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(x-> commentMapper.mapToDto(x)).toList();
    }

    @Override
    public CommentDto findCommentById(long postId, long commentId) {

        Post post =  postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment doesn't belong to Post");
        }

        return commentMapper.mapToDto(comment);
    }

    @Override
    public CommentDto updateCommentById(Long postId, Long commentId, CommentDto commentRequest) {

        Post post =  postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment doesn't belong to Post");
        }

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment updatedComment = commentRepository.save(comment);

        return commentMapper.mapToDto(updatedComment);
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {

        Post post =  postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment doesn't belong to Post");
        }

        commentRepository.delete(comment);
    }
}
