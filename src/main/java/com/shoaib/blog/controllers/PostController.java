package com.shoaib.blog.controllers;

import com.shoaib.blog.config.AppConstants;
import com.shoaib.blog.entities.Post;
import com.shoaib.blog.exceptions.ResourceNotFoundException;
import com.shoaib.blog.payloads.ApiResponse;
import com.shoaib.blog.payloads.PostDto;
import com.shoaib.blog.payloads.PostResponse;
import com.shoaib.blog.services.FileService;
import com.shoaib.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    @PostMapping("/users/{userId}/categories/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto,
            @PathVariable Long userId,
            @PathVariable Long categoryId) {
        PostDto createdPost = postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
    }

    // get by user
    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Long userId) {
        List<PostDto> posts = postService.getPostsByUser(userId);
        if (posts.isEmpty()) {
            throw new ResourceNotFoundException("Posts", " UserId: ", userId);
        }
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
    }

    // get by category
    @GetMapping("/categories/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Long categoryId) {
        List<PostDto> posts = postService.getPostsByCategory(categoryId);
        if (posts.isEmpty()) {
            throw new ResourceNotFoundException("Posts", " CategoryId: ", categoryId);
        }
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        PostResponse postResponse = postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostsById(@PathVariable Long postId) {
        PostDto postDto = postService.getPostById(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ApiResponse deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return new ApiResponse("Post is successfully deleted!", true);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long postId, @RequestBody PostDto postDto) {
        PostDto updatedPost = postService.updatePost(postId, postDto);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(
            @PathVariable("keyword") String keyword) {
        List<PostDto> postDtoList = postService.searchPosts(keyword);
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    // post image upload

    @PostMapping("post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadImage(
            @RequestParam("image")MultipartFile image,
            @PathVariable Long postId) throws IOException {

        PostDto postDto = postService.getPostById(postId);
        String fileName = fileService.uploadImage(path, image);
        postDto.setImageName(fileName);
        PostDto updatedPost = postService.updatePost(postId, postDto);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    // method to serve files
    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response) throws IOException {
        InputStream resource = fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }


}
