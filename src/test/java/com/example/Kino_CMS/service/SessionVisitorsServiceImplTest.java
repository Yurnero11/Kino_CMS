package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.Session;
import com.example.Kino_CMS.repository.SessionRepository;
import com.example.Kino_CMS.repository.UserRepository;
import com.example.Kino_CMS.service.impl.SessionVisitorsServiceImp;
import com.example.Kino_CMS.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class SessionVisitorsServiceImplTest {
    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private SessionVisitorsServiceImp sessionVisitorsServiceImp;

    @Mock
    public Logger log;

    @BeforeEach
    public void setUp() {
        sessionVisitorsServiceImp = new SessionVisitorsServiceImp();
    }

    @Test
    public void testIncrementVisitsNewSession() {
        String day = "2023-08-25";
        Session session = new Session();
        session.setDay(day);
        session.setVisits(1);

        when(sessionRepository.findByDay(day)).thenReturn(null);
        when(sessionRepository.save(any())).thenReturn(session);

        sessionVisitorsServiceImp.incrementVisits(day);

        verify(sessionRepository).save(session);
    }

    @Test
    public void testIncrementVisitsExistingSession() {
        String day = "2023-08-25";
        Session existingSession = new Session();
        existingSession.setDay(day);
        existingSession.setVisits(5);

        when(sessionRepository.findByDay(day)).thenReturn(existingSession);
        when(sessionRepository.save(any())).thenReturn(existingSession);

        sessionVisitorsServiceImp.incrementVisits(day);

        verify(sessionRepository).save(existingSession);
    }

    @Test
    public void testGetAllSessions() {
        Session session1 = new Session();
        session1.setDay("2023-08-25");
        session1.setVisits(2);

        Session session2 = new Session();
        session2.setDay("2023-08-26");
        session2.setVisits(3);


        List<Session> sessions = sessionVisitorsServiceImp.getAllSessions();
    }
}

