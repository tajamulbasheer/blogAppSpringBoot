package com.springboot.blog.springbootblogrestapi.service;

import com.springboot.blog.springbootblogrestapi.entity.Post;
import payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto post);
    List<PostDto> getAllPost();
}

