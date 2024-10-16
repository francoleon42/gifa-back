package com.gifa_api.service;

import com.gifa_api.dto.metabase.MetabaseTokenResponseDTO;

public interface IMetabaseTokenService {
    MetabaseTokenResponseDTO generateTokenForDashboard(Long dashboardId);
}
