package com.shoaib.blog.controllers;

import com.shoaib.blog.payloads.ApiResponse;
import com.shoaib.blog.payloads.CommentDto;
import com.shoaib.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/user/{userId}/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto comment,
            @PathVariable("postId") Long postId,
            @PathVariable("userId") Long userId) {

        CommentDto createdComment = commentService.createComment(comment, postId, userId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment is successfully deleted", true), HttpStatus.OK);
    }

}
