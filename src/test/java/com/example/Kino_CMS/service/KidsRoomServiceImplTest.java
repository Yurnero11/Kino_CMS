package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.KidsRoom;
import com.example.Kino_CMS.repository.KidsRoomRepository;
import com.example.Kino_CMS.service.impl.KidsRoomServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class KidsRoomServiceImplTest {
    @Mock
    private KidsRoomRepository kidsRoomRepository;

    @InjectMocks
    private KidsRoomServiceImpl kidsRoomService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveKidsRoomSuccess() {
        KidsRoom mockKidsRoom = new KidsRoom();

        when(kidsRoomRepository.save(any(KidsRoom.class))).thenReturn(mockKidsRoom);

        KidsRoom result = kidsRoomService.saveKidsRoom(mockKidsRoom);

        assertNotNull(result);
        assertEquals(mockKidsRoom, result);

        verify(kidsRoomRepository, times(1)).save(mockKidsRoom);
    }

    @Test
    public void testSaveKidsRoomError() {
        KidsRoom mockKidsRoom = new KidsRoom();

        when(kidsRoomRepository.save(eq(mockKidsRoom))).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            kidsRoomService.saveKidsRoom(mockKidsRoom);
        });

        assertEquals("Test exception", exception.getMessage());

        // Verify that the save method was called
        verify(kidsRoomRepository, times(1)).save(mockKidsRoom);
    }
    @Test
    public void testGetKidsRoomByIdExistingRoom() {
        int kidsRoomId = 1;
        KidsRoom mockKidsRoom = new KidsRoom();
        mockKidsRoom.setKids_room_id(kidsRoomId);

        when(kidsRoomRepository.findById((long) kidsRoomId)).thenReturn(Optional.of(mockKidsRoom));

        Optional<KidsRoom> result = kidsRoomService.getKidsRoomById((long) kidsRoomId);

        assertTrue(result.isPresent());
        assertEquals(mockKidsRoom, result.get());

        verify(kidsRoomRepository, times(1)).findById((long) kidsRoomId);
    }

    @Test
    public void testGetKidsRoomByIdNonExistingRoom() {
        Long kidsRoomId = 1L;

        when(kidsRoomRepository.findById(kidsRoomId)).thenReturn(Optional.empty());

        Optional<KidsRoom> result = kidsRoomService.getKidsRoomById(kidsRoomId);

        assertFalse(result.isPresent());

        verify(kidsRoomRepository, times(1)).findById(kidsRoomId);
    }

    @Test
    public void testGetAllKidsRoomSuccess() {
        KidsRoom mockKidsRoom = new KidsRoom();
        List<KidsRoom> mockKidsRoomList = Collections.singletonList(mockKidsRoom);

        when(kidsRoomRepository.findAll()).thenReturn(mockKidsRoomList);

        Iterable<KidsRoom> result = kidsRoomService.getAllKidsRoom();

        assertNotNull(result);
        assertEquals(mockKidsRoomList, result);
    }

    @Test
    public void testGetAllKidsRoomError() {
        when(kidsRoomRepository.findAll()).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            kidsRoomService.getAllKidsRoom();
        });

        assertEquals("Test exception", exception.getMessage());
    }

    @Test
    public void testGetKidsRoomByIdExistingKidsRoom() {
        int kidsRoomId = 1;
        KidsRoom mockKidsRoom = new KidsRoom();
        mockKidsRoom.setKids_room_id(kidsRoomId);

        when(kidsRoomRepository.findById((long) kidsRoomId)).thenReturn(Optional.of(mockKidsRoom));

        Optional<KidsRoom> result = kidsRoomService.getKidsRoomById((long) kidsRoomId);

        assertTrue(result.isPresent());
        assertEquals(mockKidsRoom, result.get());
    }

    @Test
    public void testGetKidsRoomByIdNonExistingKidsRoom() {
        Long kidsRoomId = 1L;

        when(kidsRoomRepository.findById(kidsRoomId)).thenReturn(Optional.empty());

        Optional<KidsRoom> result = kidsRoomService.getKidsRoomById(kidsRoomId);

        assertFalse(result.isPresent());
    }

    @Test
    public void testGetKidsRoomById_Exception() {
        MockitoAnnotations.openMocks(this);

        Long kidsRoomId = 1L;

        when(kidsRoomRepository.findById(kidsRoomId)).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> kidsRoomService.getKidsRoomById(kidsRoomId));
    }


}
