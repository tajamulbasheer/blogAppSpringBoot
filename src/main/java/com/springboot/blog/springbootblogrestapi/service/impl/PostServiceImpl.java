package com.springboot.blog.springbootblogrestapi.service.impl;

import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import org.springframework.stereotype.Service;
import com.springboot.blog.springbootblogrestapi.payload.PostDto;

import java.util.List;
import java.util.Optional;

@Service
class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto post) {

       Post savedPost= postRepository.save(convertPostDtoToEntity(post));
      return convertPostToPostDto(savedPost);

    }

    @Override
    public List<PostDto> getAllPost() {
      var postList=  postRepository.findAll();
      return postList.stream().map(this::convertPostToPostDto).toList();
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        return convertPostToPostDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        //post.setId(postDto.getId());
        post.setDescription(postDto.getDescription());
        return convertPostToPostDto( postRepository.save(post));
    }

    @Override
    public String deletePost(Long id) {
        postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        postRepository.deleteById(id);
        return String.format("deleted post with id %s",id);

    }

    private PostDto convertPostToPostDto(Post savedPost) {
        PostDto postDto= new PostDto();
        postDto.setContent(savedPost.getContent());
        postDto.setDescription(savedPost.getTitle());
        postDto.setId(savedPost.getId());
        postDto.setTitle(savedPost.getTitle());
        return postDto;
    }

    private Post convertPostDtoToEntity(PostDto postDto) {
        var post= new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return  post;

    }
}
