package com.springboot.blog.springbootblogrestapi.controller;


import com.springboot.blog.springbootblogrestapi.payload.CommentDto;
import com.springboot.blog.springbootblogrestapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") Long postId,
                                                    @RequestBody CommentDto commentDto)
    {
        return  new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);

    }
    @GetMapping("posts/{postId}/comments")
    public  ResponseEntity<List<CommentDto>> getAllCommentsByPostId(@PathVariable(value = "postId") long postId)
    {
        return  new ResponseEntity<>(commentService.getCommentsByPostId(postId),HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/comments/{id}")
    ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") Long postId,@PathVariable(value = "id") Long commentId)
    {
        return  new ResponseEntity<>(commentService.getCommentById(postId,commentId),HttpStatus.OK);

    }
    @PutMapping("/posts/{postId}/comments/{id}")
    public  ResponseEntity<CommentDto> updateComment(@PathVariable(name = "postId") long postId,@PathVariable(name = "id") long id,@RequestBody CommentDto commentDto)
    {
        return  new ResponseEntity<>(commentService.updateComment(postId,id,commentDto),HttpStatus.OK);
    }
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable(name = "postId") Long postId,@PathVariable(name = "id") Long id)
    {
        return new ResponseEntity<>(commentService.deleteComment(postId,id),HttpStatus.OK);

    }

}
