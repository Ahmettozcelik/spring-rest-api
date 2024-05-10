package com.example.blog.service.impl;

import com.example.blog.entity.Post;
import com.example.blog.entity.dto.PostDto;
import com.example.blog.entity.dto.PostResponse;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.mapper.PostMapper;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private PostMapper postMapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Override
    public Post createPost(PostDto postDto) {
        Post post = postMapper.mapToEntity(postDto);
        postRepository.save(post);

        return post;
    }

    @Override
    public PostResponse findAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        PageRequest pageable  = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content = listOfPosts.stream().map(x -> postMapper.mapToDto(x)).toList();

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public Post findPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post foundPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));

        foundPost.setTitle(postDto.getTitle());
        foundPost.setDescription(postDto.getDescription());
        foundPost.setContent(postDto.getContent());

        postRepository.save(foundPost);

        return postMapper.mapToDto(foundPost);

    }

    @Override
    public void deletePostById(Long id) {
        Post foundPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
        postRepository.delete(foundPost);
    }
}
