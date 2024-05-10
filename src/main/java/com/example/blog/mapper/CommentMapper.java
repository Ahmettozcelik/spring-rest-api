package com.example.blog.mapper;

import com.example.blog.entity.Comment;
import com.example.blog.entity.dto.CommentDto;
import com.example.blog.entity.dto.PostDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommentMapper {

    private ModelMapper mapper;

    @Autowired
    public CommentMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public CommentDto mapToDto(Comment comment){
        CommentDto commentDto = mapper.map(comment, CommentDto.class);
        return commentDto;
    }

    public Comment mapToEntity(CommentDto commentDto){
        Comment comment = mapper.map(commentDto, Comment.class);
        return comment;
    }
}
