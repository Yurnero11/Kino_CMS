package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.User;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface UserServiceInterface {
    Page<User> getAllUsers(Pageable pageable);
}
