package com.shoaib.blog.services;

import com.shoaib.blog.entities.Comment;
import com.shoaib.blog.entities.Post;
import com.shoaib.blog.entities.User;
import com.shoaib.blog.exceptions.ResourceNotFoundException;
import com.shoaib.blog.payloads.CommentDto;
import com.shoaib.blog.repositories.CommentRepo;
import com.shoaib.blog.repositories.PostRepo;
import com.shoaib.blog.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Long postId, Long userId) {
        Post post = postRepo.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", " Id ", postId));
        User user = userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", " Id ", userId));
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDto, comment);
        comment.setPost(post);
        comment.setUser(user);
        Comment savedComment = commentRepo.save(comment);
        return modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", " Id ", commentId));
        commentRepo.delete(comment);
    }
}
