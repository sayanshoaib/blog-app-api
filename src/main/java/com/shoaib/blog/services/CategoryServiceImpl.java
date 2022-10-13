package com.shoaib.blog.services;

import com.shoaib.blog.entities.Category;
import com.shoaib.blog.exceptions.ResourceNotFoundException;
import com.shoaib.blog.payloads.CategoryDto;
import com.shoaib.blog.payloads.CategoryResponse;
import com.shoaib.blog.repositories.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepo.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", " Id ", categoryId));
        categoryDto.setCategoryId(category.getCategoryId());
        BeanUtils.copyProperties(categoryDto, category);
        Category updatedCategory = categoryRepo.save(category);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", " Id ", categoryId));
        categoryRepo.delete(category);
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", " Id ", categoryId));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryResponse getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Category> categories = categoryRepo.findAll(pageRequest);
        List<CategoryDto> categoryDtoList = categories
                .stream()
                .map((category) -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDtoList);
        categoryResponse.setPageNumber(categories.getNumber());
        categoryResponse.setPageSize(categories.getSize());
        categoryResponse.setTotalElements(categories.getTotalElements());
        categoryResponse.setTotalPages(categories.getTotalPages());
        categoryResponse.setLastPage(categories.isLast());
        return categoryResponse;
    }
}
