package com.springboot.blog.springbootblogrestapi.service.impl;

import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.payload.CommentDto;
import com.springboot.blog.springbootblogrestapi.repository.CommentRepository;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import com.springboot.blog.springbootblogrestapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.blog.springbootblogrestapi.entity.Comment;

@Service
public class CommentServiceImpl implements CommentService {
 @Autowired
 private CommentRepository commentRepository;
  @Autowired
  PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository=postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment= mapToEntity(commentDto);
        Post post= postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id :",postId));
        comment.setPost(post);
       Comment postedComment= commentRepository.save(comment);
       return  mapToDto(postedComment);


    }
    private CommentDto mapToDto(Comment comment)
    {
        CommentDto commentDto= new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }
    private  Comment mapToEntity(CommentDto commentDto)
    {
        Comment comment= new Comment();
       // comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;

    }

}
