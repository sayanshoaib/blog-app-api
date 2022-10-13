package com.shoaib.blog.services;

import com.shoaib.blog.payloads.UserDto;
import com.shoaib.blog.payloads.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user, Long userId);
    UserDto getUserById(Long userId);
    UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    void deleteUser(Long userId);
}
