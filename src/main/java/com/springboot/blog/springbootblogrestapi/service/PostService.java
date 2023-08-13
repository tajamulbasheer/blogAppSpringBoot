package com.springboot.blog.springbootblogrestapi.service;

import com.springboot.blog.springbootblogrestapi.payload.PostDto;
import com.springboot.blog.springbootblogrestapi.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto post);
    PostResponse getAllPost(int pageNo, int pageSize,String sortBy,String sortDir);
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto, Long id);
    String deletePost(Long id);
}

