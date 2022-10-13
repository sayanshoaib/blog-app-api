package com.shoaib.blog.services;

import com.shoaib.blog.entities.User;
import com.shoaib.blog.exceptions.ResourceNotFoundException;
import com.shoaib.blog.payloads.UserDto;
import com.shoaib.blog.payloads.UserResponse;
import com.shoaib.blog.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = dtoToUser(userDto);
        User savedUser = userRepo.save(user);
        return userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", " Id ", userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        User updatedUser = userRepo.save(user);
        BeanUtils.copyProperties(updatedUser, userDto);
        return userDto;
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", " Id ", userId));
        return userToDto(user);
    }

    @Override
    public UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase(sortDir)) ?
                sort = Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<User> users = userRepo.findAll(pageRequest);
        List<UserDto> userDtoList = users
                .stream()
                .map(user -> userToDto(user))
                .collect(Collectors.toList());

        UserResponse userResponse = new UserResponse();
        userResponse.setContent(userDtoList);
        userResponse.setPageNumber(users.getNumber());
        userResponse.setPageSize(users.getSize());
        userResponse.setTotalElements(users.getTotalElements());
        userResponse.setTotalPages(users.getTotalPages());
        userResponse.setLastPage(users.isLast());
        return userResponse;

    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "Id", userId));
        userRepo.delete(user);
    }

    public User dtoToUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setAbout(userDto.getAbout());
//        user.setPassword(userDto.getPassword());
        return user;
    }

    public UserDto userToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
        return userDto;
    }
}
