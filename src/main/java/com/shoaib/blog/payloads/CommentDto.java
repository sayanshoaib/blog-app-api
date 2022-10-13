package com.shoaib.blog.payloads;

import com.shoaib.blog.entities.Post;
import com.shoaib.blog.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;
    private String content;
    private PostDto postDto;
    private UserDto userDto;
}
