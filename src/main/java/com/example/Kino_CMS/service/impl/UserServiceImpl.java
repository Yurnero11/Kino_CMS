package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.User;
import com.example.Kino_CMS.repository.UserRepository;
import com.example.Kino_CMS.service.UserServiceInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class UserServiceImpl implements UserServiceInterface {
    private static final Logger log = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public Iterable<User> getAllUsers() {
        try {
            log.info("Getting all Users");
            Iterable<User> result = userRepository.findAll();
            log.info("Successfully retrieved all Users");
            return result;
        } catch (Exception e) {
            log.error("Error while getting all Users", e);
            throw e;
        }
    }

    @Override
    public Page<User> searchUsers(String query, Pageable pageable) {
        try {
            log.info("Searching Users with query: {}", query);
            Page<User> result = userRepository.findByUsernameContainingIgnoreCase(query, pageable);
            log.info("Successfully searched Users with query: {}", query);
            return result;
        } catch (Exception e) {
            log.error("Error while searching Users with query: {}", query, e);
            throw e;
        }
    }

    @Override
    public User saveUser(User user) {
        try {
            log.info("Saving User: {}", user);
            User result = userRepository.save(user);
            log.info("Successfully saved User: {}", user);
            return result;
        } catch (Exception e) {
            log.error("Error while saving User: {}", user, e);
            throw e;
        }
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        try {
            log.info("Getting all Users with pagination");
            Page<User> result = userRepository.findAll(pageable);
            log.info("Successfully retrieved all Users with pagination");
            return result;
        } catch (Exception e) {
            log.error("Error while getting all Users with pagination", e);
            throw e;
        }
    }
}
