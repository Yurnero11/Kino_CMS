package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.Session;
import com.example.Kino_CMS.repository.SessionRepository;
import com.example.Kino_CMS.service.SessionVisitorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionVisitorsServiceImp implements SessionVisitorsService {
    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public void incrementVisits(String day) {
        Session session = sessionRepository.findByDay(day);
        if (session == null) {
            session = new Session();
            session.setDay(day);
            session.setVisits(1);
        } else {
            session.setVisits(session.getVisits() + 1);
        }
        sessionRepository.save(session);
    }

    @Override
    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }
}
