package com.example.blog.controller;

import com.example.blog.entity.dto.CommentDto;
import com.example.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable long postId,@RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> findCommentsByPostId(@PathVariable long postId){
        return commentService.findCommentsByPostId(postId);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> findCommentById(@PathVariable long postId, @PathVariable long commentId){
        return new ResponseEntity<>(commentService.findCommentById(postId,commentId), HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable long postId, @PathVariable long commentId, @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.updateCommentById(postId,commentId,commentDto),HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentById(@PathVariable Long postId, @PathVariable Long commentId){
        commentService.deleteCommentById(postId, commentId);
        return new ResponseEntity<>("Comment deleted successfully.",HttpStatus.OK);
    }
}
