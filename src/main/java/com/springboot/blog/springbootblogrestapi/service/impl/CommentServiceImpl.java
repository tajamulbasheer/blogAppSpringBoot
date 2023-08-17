package com.springboot.blog.springbootblogrestapi.service.impl;

import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.BlogAPIException;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.payload.CommentDto;
import com.springboot.blog.springbootblogrestapi.repository.CommentRepository;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import com.springboot.blog.springbootblogrestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.springboot.blog.springbootblogrestapi.entity.Comment;

import java.nio.ReadOnlyBufferException;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
 @Autowired
 private CommentRepository commentRepository;
  @Autowired
  private PostRepository postRepository;
  @Autowired
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository=postRepository;
        this.modelMapper=mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment= mapToEntity(commentDto);
        Post post= postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id :",postId));
        comment.setPost(post);
       Comment postedComment= commentRepository.save(comment);
       return  mapToDto(postedComment);


    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        //reterive comments by postID;
        List<Comment> comments=commentRepository.findByPostId(postId);
        return  comments.stream().map(this::mapToDto).toList();
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post= postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id :",postId));
        //reterive Comment by ID;
        Comment comment= commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment","id",commentId));
        if(!comment.getPost().getId().equals(post.getId()))
        {
            throw  new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }
        return mapToDto(comment);

    }

    @Override
    public CommentDto updateComment(Long postId, long commentId, CommentDto commentDto) {

        Post post= postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id :",postId));
        //reterive Comment by ID;
        Comment comment= commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment","id",commentId));
        if(!comment.getPost().getId().equals(post.getId()))
        {
            throw  new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }
        comment.setBody(commentDto.getBody());
      var savedComment=  commentRepository.save(comment);
      return mapToDto(savedComment);



    }

    @Override
    public String deleteComment(Long postId, long commentId) {
        Post post =postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id :",postId));
        Comment comment= commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment","id",commentId));
        if(!comment.getPost().getId().equals(post.getId()))
        {
            throw  new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }
        commentRepository.deleteById(commentId);
        return String.format("Deleted comment on post with id %d and comment id is %d",postId,commentId);


    }

    private CommentDto mapToDto(Comment comment)
    {
        //CommentDto commentDto= new CommentDto();
        return  modelMapper.map(comment,CommentDto.class);
//        CommentDto commentDto= new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        //return commentDto;
    }
    private  Comment mapToEntity(CommentDto commentDto)
    {
        return  modelMapper.map(commentDto,Comment.class);
//        Comment comment= new Comment();
//       // comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
//        return comment;

    }

}
