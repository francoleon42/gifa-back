package com.gifa_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.gifa_api.repository.IPingRepository;
import com.gifa_api.service.IPingService;

@Service
@RequiredArgsConstructor
public class PingServiceImpl implements IPingService {
    private final IPingRepository pingRepository;

    @Override
    public String getPong() {
        return pingRepository.getPong();
    }
}
