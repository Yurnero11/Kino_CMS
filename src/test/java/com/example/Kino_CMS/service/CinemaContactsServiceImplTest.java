package com.example.Kino_CMS.service;
import com.example.Kino_CMS.entity.CinemaContact;
import com.example.Kino_CMS.repository.CinemaContactsRepository;
import com.example.Kino_CMS.service.impl.CinemaContactsServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CinemaContactsServiceImplTest {
    @Mock
    private CinemaContactsRepository contactsRepository;

    @InjectMocks
    private CinemaContactsServiceImpl cinemaContactsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveCinemaContactSuccess() {
        CinemaContact mockCinemaContact = new CinemaContact();

        when(contactsRepository.save(any(CinemaContact.class))).thenReturn(mockCinemaContact);

        CinemaContact result = cinemaContactsService.saveCinemaContact(mockCinemaContact);

        assertNotNull(result);
        assertEquals(mockCinemaContact, result);

        verify(contactsRepository, times(1)).save(mockCinemaContact);
    }

    @Test
    public void testSaveCinemaContactError() {
        CinemaContact mockCinemaContact = new CinemaContact();
        when(contactsRepository.save(eq(mockCinemaContact))).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cinemaContactsService.saveCinemaContact(mockCinemaContact);
        });

        assertEquals("Test exception", exception.getMessage());

        // Verify that the save method was called
        verify(contactsRepository, times(1)).save(mockCinemaContact);
    }

    @Test
    public void testGetAllCinemaContactSuccess() {
        List<CinemaContact> mockCinemaContactList = new ArrayList<>();
        mockCinemaContactList.add(new CinemaContact());
        mockCinemaContactList.add(new CinemaContact());

        when(contactsRepository.findAll()).thenReturn(mockCinemaContactList);

        Iterable<CinemaContact> result = cinemaContactsService.getAllCinemaContact();

        assertNotNull(result);
        assertEquals(mockCinemaContactList, (List<CinemaContact>) result);

        verify(contactsRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllCinemaContactError() {
        when(contactsRepository.findAll()).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cinemaContactsService.getAllCinemaContact();
        });

        assertEquals("Test exception", exception.getMessage());

        // Verify that the findAll method was called
        verify(contactsRepository, times(1)).findAll();
    }

    @Test
    public void testGetCinemaContactByIdSuccess() {
        Long contactId = 1L;
        CinemaContact mockCinemaContact = new CinemaContact();

        when(contactsRepository.findById(contactId)).thenReturn(Optional.of(mockCinemaContact));

        Optional<CinemaContact> result = cinemaContactsService.getCinemaContactById(contactId);

        assertTrue(result.isPresent());
        assertEquals(mockCinemaContact, result.get());

        verify(contactsRepository, times(1)).findById(contactId);
    }

    @Test
    public void testGetCinemaContactByIdNotFound() {
        Long contactId = 1L;

        when(contactsRepository.findById(contactId)).thenReturn(Optional.empty());

        Optional<CinemaContact> result = cinemaContactsService.getCinemaContactById(contactId);

        assertFalse(result.isPresent());

        verify(contactsRepository, times(1)).findById(contactId);
    }

    @Test
    public void testGetCinemaContactByIdError() {
        Long contactId = 1L;

        when(contactsRepository.findById(contactId)).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cinemaContactsService.getCinemaContactById(contactId);
        });

        assertEquals("Test exception", exception.getMessage());

        // Verify that the findById method was called
        verify(contactsRepository, times(1)).findById(contactId);
    }

    @Test
    public void testDeleteCinemaContactSuccess() {
        CinemaContact mockCinemaContact = new CinemaContact();

        cinemaContactsService.delete(mockCinemaContact);

        verify(contactsRepository, times(1)).delete(mockCinemaContact);
    }

    @Test
    public void testDeleteCinemaContactError() {
        CinemaContact mockCinemaContact = new CinemaContact();

        doThrow(new RuntimeException("Test exception")).when(contactsRepository).delete(mockCinemaContact);

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cinemaContactsService.delete(mockCinemaContact);
        });

        assertEquals("Test exception", exception.getMessage());

        // Verify that the delete method was called
        verify(contactsRepository, times(1)).delete(mockCinemaContact);
    }
}
