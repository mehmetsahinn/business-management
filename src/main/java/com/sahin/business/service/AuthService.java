package com.sahin.business.service;

import com.sahin.business.dto.AuthRequest;
import com.sahin.business.dto.AuthResponse;

public interface AuthService {
    public AuthResponse login(AuthRequest request);
}
