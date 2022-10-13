package com.shoaib.blog.services;

import com.shoaib.blog.entities.Category;
import com.shoaib.blog.entities.Post;
import com.shoaib.blog.entities.User;
import com.shoaib.blog.exceptions.ResourceNotFoundException;
import com.shoaib.blog.payloads.PostDto;
import com.shoaib.blog.payloads.PostResponse;
import com.shoaib.blog.repositories.CategoryRepo;
import com.shoaib.blog.repositories.PostRepo;
import com.shoaib.blog.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Long userId, Long categoryId) {
        User user = userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", " Id ", userId));
        Category category = categoryRepo.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", " Id ", categoryId));
        Post post = modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post newPost = postRepo.save(post);
        return modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(Long postId, PostDto postDto) {
        Post post = postRepo.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", " Id ", postId));
        BeanUtils.copyProperties(postDto, post);
        post.setPostId(postId);
        post.setAddedDate(new Date());
        Post updatedPost = postRepo.save(post);
        return modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Long postId) {
        Post post = postRepo.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", " Id ", postId));
        postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase(sortDir)) ?
                sort = Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        PageRequest p = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> posts = postRepo.findAll(p);

        List<PostDto> postDtoList = posts
                .stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtoList);
        postResponse.setPageNumber(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLastPage(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Long postId) {
        Post post = postRepo.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", " Id ", postId));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", " Id ", categoryId));
        List<Post> posts = postRepo.findByCategory(category);
        List<PostDto> postDtoList = posts
                .stream()
                .map((post -> modelMapper.map(post, PostDto.class)))
                .collect(Collectors.toList());

        return postDtoList;
    }

    @Override
    public List<PostDto> getPostsByUser(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", " Id ", userId));
        List<Post> posts = postRepo.findByUser(user);
        List<PostDto> postDtoList = posts
                .stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postDtoList;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = postRepo.searchByTitle(keyword);
        List<PostDto> postDtoList = posts
                .stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postDtoList;
    }
}
