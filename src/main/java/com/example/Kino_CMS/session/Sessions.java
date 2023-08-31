package com.example.Kino_CMS.session;

import com.example.Kino_CMS.entity.User;
import com.example.Kino_CMS.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class Sessions {
    private static final Logger log = LogManager.getLogger(Sessions.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest request;


    public void setUserIdInSession() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                String username = authentication.getName();
                User user = userRepository.findByUsername(username);
                if (user != null) {
                    Integer userId = user.getId();
                    HttpSession session = request.getSession();
                    session.setAttribute("userId", userId);
                    log.info("User ID {} set in session", userId);
                }
            }
        } catch (Exception e) {
            log.error("Error while setting user ID in session", e);
            throw e;
        }
    }


    public Integer getUserIdFromSession() {
        try {
            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId != null) {
                log.info("Retrieved user ID {} from session", userId);
            } else {
                log.info("User ID not found in session");
            }
            return userId;
        } catch (Exception e) {
            log.error("Error while getting user ID from session", e);
            throw e;
        }
    }
}
