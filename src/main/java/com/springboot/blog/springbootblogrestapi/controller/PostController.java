package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payload.PostDto;

import java.util.List;

@RestController
@RequestMapping("api/posts")
class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody  PostDto postDto)
    {
       var savedPost= postService.createPost(postDto);
       return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }
    @GetMapping
    public  ResponseEntity<List<PostDto>> getAllPost()
    {
        return new ResponseEntity<>(postService.getAllPost(),HttpStatus.OK);

    }

}
