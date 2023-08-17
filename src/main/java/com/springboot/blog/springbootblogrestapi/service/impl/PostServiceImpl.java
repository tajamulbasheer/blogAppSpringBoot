package com.springboot.blog.springbootblogrestapi.service.impl;

import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.payload.PostResponse;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.springboot.blog.springbootblogrestapi.payload.PostDto;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository,ModelMapper mapper) {
        this.postRepository = postRepository;
        this.modelMapper=mapper;
    }

    @Override
    public PostDto createPost(PostDto post) {

       Post savedPost= postRepository.save(convertPostDtoToEntity(post));
      return convertPostToPostDto(savedPost);

    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize,String sortBy,String sortDir) {
        Sort sort= sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :Sort.by(sortBy).descending();
        var page= PageRequest.of(pageNo,pageSize, sort);
        var  createdPage=postRepository.findAll(page);
      var postList= createdPage.getContent();
      var content=postList.stream().map(this::convertPostToPostDto).toList();
      var postResponse= new PostResponse();
      postResponse.setContent(content);
      postResponse.setPageNo(createdPage.getNumber());
      postResponse.setPageSize(createdPage.getSize());
      postResponse.setTotalElements(createdPage.getTotalElements());
      postResponse.setTotalPages(createdPage.getTotalPages());
      postResponse.setLast(createdPage.isLast());
      return postResponse;

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
        return  modelMapper.map(savedPost,PostDto.class);
//        PostDto postDto= new PostDto();
//        postDto.setContent(savedPost.getContent());
//        postDto.setDescription(savedPost.getTitle());
//        postDto.setId(savedPost.getId());
//        postDto.setTitle(savedPost.getTitle());
//        return postDto;
    }

    private Post convertPostDtoToEntity(PostDto postDto) {
       return  modelMapper.map(postDto,Post.class);
//        var post= new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
//        return  post;

    }
}
