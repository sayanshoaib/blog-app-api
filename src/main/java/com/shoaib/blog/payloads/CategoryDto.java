package com.shoaib.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Long categoryId;
    @NotBlank
    @Size(min = 2, message = "Password must be min of 4 chars and max 20 chars!!")
    private String categoryTitle;

    @NotBlank
    @Size(min = 4, max = 50, message = "Password must be min of 4 chars and max 20 chars!!")
    private String CategoryDescription;
}
