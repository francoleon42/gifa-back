package com.gifa_api.repository.impl;

import org.springframework.stereotype.Service;
import com.gifa_api.repository.IPingRepository;

@Service
public class PingRepository implements IPingRepository {
    @Override
    public String getPong() {
        return "pong";
    }
}
