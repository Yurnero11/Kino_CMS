package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.Advertising;
import org.springframework.stereotype.Service;

public interface AdvertisingService {
    Iterable<Advertising> getAllAdvertising();
}
