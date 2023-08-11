package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.VipHall;

import java.util.Optional;

public interface VipHallService {
    VipHall saveVipHall(VipHall vipHall);
    public Iterable<VipHall> getAllVipHals();
    Optional<VipHall> findById(Long vipHall_id);
}
