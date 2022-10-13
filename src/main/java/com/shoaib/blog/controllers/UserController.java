package com.shoaib.blog.controllers;


import com.shoaib.blog.config.AppConstants;
import com.shoaib.blog.payloads.ApiResponse;
import com.shoaib.blog.payloads.UserDto;
import com.shoaib.blog.payloads.UserResponse;
import com.shoaib.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUserDto = userService.createUser(userDto);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable("userId") Long userId, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity(new ApiResponse("User has been successfully deleted", true), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity(new ApiResponse("User has not found", false), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<UserResponse> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        return ResponseEntity.ok(userService.getAllUsers(pageNumber, pageSize, sortBy, sortDir));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }


}
