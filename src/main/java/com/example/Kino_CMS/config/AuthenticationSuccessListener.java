package com.example.Kino_CMS.config;

import com.example.Kino_CMS.service.impl.SessionServiceImpl;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final SessionServiceImpl sessionServiceImpl;

    public AuthenticationSuccessListener(SessionServiceImpl sessionServiceImpl) {
        this.sessionServiceImpl = sessionServiceImpl;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        sessionServiceImpl.setUserIdInSession();
    }
}
