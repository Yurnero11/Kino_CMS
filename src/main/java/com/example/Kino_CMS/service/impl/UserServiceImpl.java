package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.User;
import com.example.Kino_CMS.repository.UserRepository;
import com.example.Kino_CMS.service.UserServiceInterface;
import org.springframework.data.domain.Page;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class UserServiceImpl implements UserServiceInterface {
    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByEmails(List<String> emails) {
        return userRepository.findByEmailIn(emails);
    }

    public Page<User> searchUsers(String query, Pageable pageable) {
        return userRepository.findByUsernameContainingIgnoreCase(query, pageable);
    }


    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPasswordHash(),
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")) // Замените "ROLE_ADMIN" на роли пользователя, если нужно
        );
    }
}
