package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.KidsRoom;

import java.util.Optional;

public interface KidsRoomService {
    KidsRoom saveKidsRoom(KidsRoom kidsRoom);
    Optional<KidsRoom> getKidsRoomById(Long kidsRoomId);

    Iterable<KidsRoom> getAllKidsRoom();
}
