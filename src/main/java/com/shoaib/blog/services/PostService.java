package com.shoaib.blog.services;

import com.shoaib.blog.entities.Post;
import com.shoaib.blog.payloads.PostDto;
import com.shoaib.blog.payloads.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    PostDto createPost(PostDto postDto, Long userId, Long categoryId);
    PostDto updatePost(Long postId, PostDto postDto);
    void deletePost(Long postID);
    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    PostDto getPostById(Long postId);
    List<PostDto> getPostsByCategory(Long categoryId);
    List<PostDto> getPostsByUser(Long userId);
    // search posts
    List<PostDto> searchPosts(String keyword);
}
