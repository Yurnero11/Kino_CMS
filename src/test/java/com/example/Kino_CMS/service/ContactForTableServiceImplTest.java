package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.Contact_for_table;
import com.example.Kino_CMS.repository.ContactForTableRepository;
import com.example.Kino_CMS.service.impl.ContactForTableServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ContactForTableServiceImplTest {

    @Mock
    private ContactForTableRepository contactForTableRepository;

    @InjectMocks
    private ContactForTableServiceImpl contactForTableService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetContactForTableSuccess() {
        Long contactId = 1L;
        Contact_for_table mockContact = new Contact_for_table();

        when(contactForTableRepository.findById(contactId)).thenReturn(Optional.of(mockContact));

        Optional<Contact_for_table> result = contactForTableService.getContactForTable(contactId);

        assertTrue(result.isPresent());
        assertEquals(mockContact, result.get());
    }

    @Test
    public void testGetContactForTableNotFound() {
        Long contactId = 1L;

        when(contactForTableRepository.findById(contactId)).thenReturn(Optional.empty());

        Optional<Contact_for_table> result = contactForTableService.getContactForTable(contactId);

        assertFalse(result.isPresent());
    }

    @Test
    public void testGetContactForTableError() {
        Long contactId = 1L;

        when(contactForTableRepository.findById(contactId)).thenThrow(new RuntimeException("Test exception"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contactForTableService.getContactForTable(contactId);
        });

        assertEquals("Test exception", exception.getMessage());
    }

    @Test
    public void testSaveContact_for_tableSuccess() {
        Contact_for_table mockContact = new Contact_for_table();

        when(contactForTableRepository.save(any(Contact_for_table.class))).thenReturn(mockContact);

        Contact_for_table result = contactForTableService.saveContact_for_table(mockContact);

        assertNotNull(result);
        assertEquals(mockContact, result);

        verify(contactForTableRepository, times(1)).save(mockContact);
    }

    @Test
    public void testSaveContact_for_tableError() {
        Contact_for_table mockContact = new Contact_for_table();

        when(contactForTableRepository.save(eq(mockContact))).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contactForTableService.saveContact_for_table(mockContact);
        });

        assertEquals("Test exception", exception.getMessage());

        // Verify that the save method was called
        verify(contactForTableRepository, times(1)).save(mockContact);
    }

    @Test
    public void testGetAllContactsSuccess() {
        Contact_for_table mockContact = new Contact_for_table();
        List<Contact_for_table> mockContactList = Collections.singletonList(mockContact);

        when(contactForTableRepository.findAll()).thenReturn(mockContactList);

        Iterable<Contact_for_table> result = contactForTableService.getAllContacts();

        assertNotNull(result);
        assertEquals(mockContactList, result);
    }

    @Test
    public void testGetAllContactsError() {
        when(contactForTableRepository.findAll()).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contactForTableService.getAllContacts();
        });

        assertEquals("Test exception", exception.getMessage());
    }
}