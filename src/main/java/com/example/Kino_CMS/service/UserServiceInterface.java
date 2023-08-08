package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.User;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserServiceInterface {
    Page<User> getAllUsers(Pageable pageable);

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;


}
