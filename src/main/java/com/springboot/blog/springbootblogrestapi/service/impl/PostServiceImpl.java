package com.springboot.blog.springbootblogrestapi.service.impl;

import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import org.springframework.stereotype.Service;
import payload.PostDto;

import java.util.List;
import java.util.stream.Collectors;

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
