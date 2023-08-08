package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.repository.VipHallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VipHallServiceImpl {
    private final VipHallRepository vipHallRepository;

    @Autowired
    public VipHallServiceImpl(VipHallRepository vipHallRepository) {
        this.vipHallRepository = vipHallRepository;
    }
}
