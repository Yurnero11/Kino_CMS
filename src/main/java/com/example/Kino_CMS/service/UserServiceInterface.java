package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.User;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserServiceInterface {
    Page<User> getAllUsers(Pageable pageable);

    Page<User> searchUsers(String query, Pageable pageable);
    Iterable<User> getAllUsers();

    User saveUser(User user);


}
