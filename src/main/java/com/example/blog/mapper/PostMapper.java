package com.example.blog.mapper;

import com.example.blog.entity.Post;
import com.example.blog.entity.dto.PostDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostMapper {

    private ModelMapper mapper;

    @Autowired
    public PostMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public PostDto mapToDto(Post post){
        PostDto postDto = mapper.map(post, PostDto.class);
        return postDto;
    }

    public Post mapToEntity(PostDto postDto){
        Post post = mapper.map(postDto, Post.class);
        return post;
    }
}
