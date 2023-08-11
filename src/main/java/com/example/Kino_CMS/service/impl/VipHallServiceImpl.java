package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.VipHall;
import com.example.Kino_CMS.repository.VipHallRepository;
import com.example.Kino_CMS.service.VipHallService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VipHallServiceImpl implements VipHallService {
    private static final Logger log = LogManager.getLogger(VipHallServiceImpl.class);

    @Autowired
    private VipHallRepository vipHallRepository;

    @Override
    public VipHall saveVipHall(VipHall vipHall) {
        try {
            log.info("Saving VipHall: {}", vipHall);
            VipHall result = vipHallRepository.save(vipHall);
            log.info("Successfully saved VipHall: {}", vipHall);
            return result;
        } catch (Exception e) {
            log.error("Error while saving VipHall: {}", vipHall, e);
            throw e;
        }
    }

    @Override
    public Optional<VipHall> findById(Long vipHall_id) {
        try {
            log.info("Getting VipHall by ID: {}", vipHall_id);
            Optional<VipHall> result = vipHallRepository.findById(vipHall_id);
            if (result.isPresent()) {
                log.info("Successfully retrieved VipHall by ID: {}", vipHall_id);
            } else {
                log.info("VipHall with ID {} not found", vipHall_id);
            }
            return result;
        } catch (Exception e) {
            log.error("Error while getting VipHall by ID: {}", vipHall_id, e);
            throw e;
        }
    }

    @Override
    public Iterable<VipHall> getAllVipHals() {
        try {
            log.info("Getting all VipHalls");
            Iterable<VipHall> result = vipHallRepository.findAll();
            log.info("Successfully retrieved all VipHalls");
            return result;
        } catch (Exception e) {
            log.error("Error while getting all VipHalls", e);
            throw e;
        }
    }
}
