package com.springboot.blog.springbootblogrestapi.service;

import com.springboot.blog.springbootblogrestapi.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto post);
    List<PostDto> getAllPost();
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto, Long id);
    String deletePost(Long id);
}

