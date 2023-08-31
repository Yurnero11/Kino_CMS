package com.example.Kino_CMS.config;

import com.example.Kino_CMS.session.Sessions;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final Sessions sessions;

    public AuthenticationSuccessListener(Sessions sessions) {
        this.sessions = sessions;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        sessions.setUserIdInSession();
    }
}
