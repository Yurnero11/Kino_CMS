package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.News;
import com.example.Kino_CMS.entity.User;
import com.example.Kino_CMS.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByEmails(List<String> emails) {
        return userRepository.findByEmailIn(emails);
    }
}
