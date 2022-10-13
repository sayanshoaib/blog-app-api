package com.shoaib.blog.services;

import com.shoaib.blog.payloads.CommentDto;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {

    CommentDto createComment(CommentDto commentDto, Long postId, Long userId);
    void deleteComment(Long commentId);

}
