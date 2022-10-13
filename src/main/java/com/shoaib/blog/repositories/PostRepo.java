package com.shoaib.blog.repositories;

import com.shoaib.blog.entities.Category;
import com.shoaib.blog.entities.Post;
import com.shoaib.blog.entities.User;
import com.shoaib.blog.payloads.PostDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    @Query("select p from Post p where lower(p.title) like concat('%',:keyword,'%')")
    List<Post> searchByTitle(@Param("keyword") String keyword);
}
