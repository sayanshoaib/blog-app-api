package com.shoaib.blog.repositories;

import com.shoaib.blog.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface CommentRepo extends JpaRepository<Comment, Long> {
}
