package com.shoaib.blog.payloads;

import com.shoaib.blog.entities.Category;
import com.shoaib.blog.entities.Comment;
import com.shoaib.blog.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private String title;
    private String content;
    private String imageName;
    private Date addedDate;
    private UserDto user;
    private CategoryDto category;
    private Set<CommentDto> comments = new HashSet<>();
}
