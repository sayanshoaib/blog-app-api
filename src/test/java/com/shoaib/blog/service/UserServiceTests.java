package com.shoaib.blog.service;

import com.shoaib.blog.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepo userRepo;

    @Test
    void contextLoads() {
    }

    @Test
    public void repoTest() {
        String className = userRepo.getClass().getName();
        String packName = userRepo.getClass().getPackageName();
        System.out.println(className);
        System.out.println(packName);
    }

//    @Test
//    public void getUsersTest() {
//        UserResponse allUsers = userService
//                .getAllUsers(1, 5, AppConstants.SORT_BY, AppConstants.SORT_DIR);
//        Assertions.assertEquals(generateUserResponse(), allUsers);
//    }
//
//    public static UserResponse generateUserResponse() {
//        List<UserDto> userDtoList = new ArrayList<>();
//
//        UserDto userDto1 = new UserDto();
//        userDto1.setName("Shoaib");
//        userDto1.setEmail("shoaib@gmail.com");
//        userDto1.setAbout("ajdsadjasd");
//        userDto1.setPassword("asa");
//
//        UserDto userDto2 = new UserDto();
//        userDto2.setName("adib");
//        userDto2.setEmail("adaib@gmail.com");
//        userDto2.setAbout("ajdsadjasd");
//        userDto2.setPassword("asa");
//
//        userDtoList.add(userDto1);
//        userDtoList.add(userDto2);
//
//        UserResponse userResponse = new UserResponse();
//        userResponse.setContent(userDtoList);
//        userResponse.setPageNumber(0);
//        userResponse.setPageSize(5);
//        userResponse.setTotalElements(2L);
//        userResponse.setTotalPages(1);
//        userResponse.setLastPage(true);
//
//        return userResponse;
//    }

}
