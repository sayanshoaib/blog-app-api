package com.shoaib.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotEmpty
    @Size(min = 3, message = "Username must be min of 4 characters")
    private String name;

    @Email(message = "Email address is not valid!!")
    private String email;

    @NotEmpty
    @Size(min = 4, max = 20, message = "Password must be min of 4 chars and max 20 chars!!")
    private String password;

    @NotEmpty
    private String about;
    private Set<CommentDto> comments = new HashSet<>();
}
