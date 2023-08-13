package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.payload.PostResponse;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import com.springboot.blog.springbootblogrestapi.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.springboot.blog.springbootblogrestapi.payload.PostDto;

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
    public  ResponseEntity<PostResponse> getAllPost(
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO,required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(name="sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(name="sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir

    )
    {
        return new ResponseEntity<>(postService.getAllPost(pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);

    }
    @GetMapping("/{id}")
    public  ResponseEntity<PostDto> getPostById(@PathVariable Long id)
    {
        return new ResponseEntity<>(postService.getPostById(id),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public  ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Long id)
    {
        return new ResponseEntity<>(postService.updatePost(postDto,id),HttpStatus.OK);

    }
    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deletePost(@PathVariable Long id)
    {
        return  new ResponseEntity<>(postService.deletePost(id),HttpStatus.OK);
    }

}
