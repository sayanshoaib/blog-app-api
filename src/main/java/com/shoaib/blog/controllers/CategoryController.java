package com.shoaib.blog.controllers;

import com.shoaib.blog.config.AppConstants;
import com.shoaib.blog.payloads.ApiResponse;
import com.shoaib.blog.payloads.CategoryDto;
import com.shoaib.blog.payloads.CategoryResponse;
import com.shoaib.blog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/categories")
    public ResponseEntity<CategoryDto>  createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategory = categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryDto> updatedCategory(
            @Valid @PathVariable Long categoryId, @RequestBody CategoryDto categoryDto) {
        CategoryDto updateCategory = categoryService.updateCategory(categoryId, categoryDto);
        return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.OK);
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<ApiResponse>(
                new ApiResponse("Category is deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long categoryId) {
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<CategoryResponse> getCategories(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        CategoryResponse categoryResponse = categoryService.getCategories(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }
}
