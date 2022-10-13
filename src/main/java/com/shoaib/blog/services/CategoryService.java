package com.shoaib.blog.services;

import com.shoaib.blog.entities.Category;
import com.shoaib.blog.payloads.CategoryDto;
import com.shoaib.blog.payloads.CategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto);
    void deleteCategory(Long categoryId);
    CategoryDto getCategoryById(Long categoryId);
    CategoryResponse getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
}
