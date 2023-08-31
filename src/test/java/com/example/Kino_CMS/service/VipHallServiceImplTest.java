package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.KidsRoom;
import com.example.Kino_CMS.entity.VipHall;
import com.example.Kino_CMS.repository.VipHallRepository;
import com.example.Kino_CMS.service.impl.VipHallServiceImpl;
import org.apache.logging.log4j.Logger;
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

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class VipHallServiceImplTest {
    @Mock
    private VipHallRepository vipHallRepository;

    @InjectMocks
    private VipHallServiceImpl vipHallService;

    @Mock
    public Logger log;

    @Test
    public void testSaveKidsRoomSuccess() {
        VipHall mockKidsRoom = new VipHall();

        when(vipHallRepository.save(any(VipHall.class))).thenReturn(mockKidsRoom);

        VipHall result = vipHallService.saveVipHall(mockKidsRoom);

        assertNotNull(result);
        assertEquals(mockKidsRoom, result);

        verify(vipHallRepository, times(1)).save(mockKidsRoom);
    }

    @Test
    public void testSaveKidsRoomError() {
        VipHall mockKidsRoom = new VipHall();

        when(vipHallRepository.save(eq(mockKidsRoom))).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            vipHallService.saveVipHall(mockKidsRoom);
        });

        assertEquals("Test exception", exception.getMessage());

        // Verify that the save method was called
        verify(vipHallRepository, times(1)).save(mockKidsRoom);
    }
    @Test
    public void testGetKidsRoomByIdExistingRoom() {
        int kidsRoomId = 1;
        VipHall mockKidsRoom = new VipHall();
        mockKidsRoom.setVip_hall_id(kidsRoomId);

        when(vipHallRepository.findById((long) kidsRoomId)).thenReturn(Optional.of(mockKidsRoom));

        Optional<VipHall> result = vipHallService.findById((long) kidsRoomId);

        assertTrue(result.isPresent());
        assertEquals(mockKidsRoom, result.get());

        verify(vipHallRepository, times(1)).findById((long) kidsRoomId);
    }

    @Test
    public void testGetKidsRoomByIdNonExistingRoom() {
        Long kidsRoomId = 1L;

        when(vipHallRepository.findById(kidsRoomId)).thenReturn(Optional.empty());

        Optional<VipHall> result = vipHallService.findById(kidsRoomId);

        assertFalse(result.isPresent());

        verify(vipHallRepository, times(1)).findById(kidsRoomId);
    }

    @Test
    public void testGetAllKidsRoomSuccess() {
        VipHall mockKidsRoom = new VipHall();
        List<VipHall> mockKidsRoomList = Collections.singletonList(mockKidsRoom);

        when(vipHallRepository.findAll()).thenReturn(mockKidsRoomList);

        Iterable<VipHall> result = vipHallService.getAllVipHals();

        assertNotNull(result);
        assertEquals(mockKidsRoomList, result);
    }

    @Test
    public void testGetAllKidsRoomError() {
        when(vipHallRepository.findAll()).thenThrow(new RuntimeException("Test exception"));

        // Use assertThrows to verify that an exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            vipHallService.getAllVipHals();
        });

        assertEquals("Test exception", exception.getMessage());
    }

    @Test
    public void testGetKidsRoomByIdExistingKidsRoom() {
        int kidsRoomId = 1;
        VipHall mockKidsRoom = new VipHall();
        mockKidsRoom.setVip_hall_id(kidsRoomId);

        when(vipHallRepository.findById((long) kidsRoomId)).thenReturn(Optional.of(mockKidsRoom));

        Optional<VipHall> result = vipHallService.findById((long) kidsRoomId);

        assertTrue(result.isPresent());
        assertEquals(mockKidsRoom, result.get());
    }

    @Test
    public void testGetKidsRoomByIdNonExistingKidsRoom() {
        Long kidsRoomId = 1L;

        when(vipHallRepository.findById(kidsRoomId)).thenReturn(Optional.empty());

        Optional<VipHall> result = vipHallService.findById(kidsRoomId);

        assertFalse(result.isPresent());
    }

    @Test
    public void testFindById_Exception() {
        MockitoAnnotations.openMocks(this);

        Long vipHallId = 1L;

        when(vipHallRepository.findById(vipHallId)).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> vipHallService.findById(vipHallId));
    }

}
