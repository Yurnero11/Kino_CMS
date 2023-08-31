package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.Session;

import java.util.List;

public interface SessionVisitorsService {
    void incrementVisits(String day);
    List<Session> getAllSessions();
}
