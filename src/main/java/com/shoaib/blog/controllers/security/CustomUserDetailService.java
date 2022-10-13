package com.shoaib.blog.controllers.security;

import com.shoaib.blog.entities.User;
import com.shoaib.blog.exceptions.UserNotFoundWithEmailException;
import com.shoaib.blog.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // loading user from database by username
        User user = userRepo.findByEmail(username).orElseThrow(() ->
                new UserNotFoundWithEmailException("User", " email ", username));
        return user;
    }


}
