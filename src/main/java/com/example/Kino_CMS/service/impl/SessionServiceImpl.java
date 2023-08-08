package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.User;
import com.example.Kino_CMS.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl {

    private final UserRepository userRepository;
    private final HttpServletRequest request;

    @Autowired
    public SessionServiceImpl(UserRepository userRepository, HttpServletRequest request) {
        this.userRepository = userRepository;
        this.request = request;
    }

    // Метод, который вызывается при успешной аутентификации
    public void setUserIdInSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username);
            if (user != null) {
                Integer userId = user.getId();
                // Сохраняем userId в сессии
                HttpSession session = request.getSession();
                session.setAttribute("userId", userId);
            }
        }
    }

    // Метод для получения userId из сессии
    public Integer getUserIdFromSession() {
        HttpSession session = request.getSession();
        return (Integer) session.getAttribute("userId");
    }
}
