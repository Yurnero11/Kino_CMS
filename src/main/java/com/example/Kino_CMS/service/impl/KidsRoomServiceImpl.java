package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.KidsRoom;
import com.example.Kino_CMS.repository.KidsRoomRepository;
import com.example.Kino_CMS.service.KidsRoomService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KidsRoomServiceImpl implements KidsRoomService {
    private static final Logger log = LogManager.getLogger(KidsRoomServiceImpl.class);

    @Autowired
    private KidsRoomRepository kidsRoomRepository;

    @Override
    public KidsRoom saveKidsRoom(KidsRoom kidsRoom) {
        try {
            log.info("Saving KidsRoom: {}", kidsRoom);
            KidsRoom result = kidsRoomRepository.save(kidsRoom);
            log.info("Successfully saved KidsRoom: {}", kidsRoom);
            return result;
        } catch (Exception e) {
            log.error("Error while saving KidsRoom: {}", kidsRoom, e);
            throw e;
        }
    }

    @Override
    public Optional<KidsRoom> getKidsRoomById(Long kidsRoomId) {
        try {
            log.info("Getting KidsRoom by ID: {}", kidsRoomId);
            Optional<KidsRoom> result = kidsRoomRepository.findById(kidsRoomId);
            if (result.isPresent()) {
                log.info("Successfully retrieved KidsRoom by ID: {}", kidsRoomId);
            } else {
                log.info("KidsRoom with ID {} not found", kidsRoomId);
            }
            return result;
        } catch (Exception e) {
            log.error("Error while getting KidsRoom by ID: {}", kidsRoomId, e);
            throw e;
        }
    }

    @Override
    public Iterable<KidsRoom> getAllKidsRoom() {
        try {
            log.info("Getting all KidsRoom");
            Iterable<KidsRoom> result = kidsRoomRepository.findAll();
            log.info("Successfully retrieved all KidsRoom");
            return result;
        } catch (Exception e) {
            log.error("Error while getting all KidsRoom", e);
            throw e;
        }
    }
}
