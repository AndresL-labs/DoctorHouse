package com.example.doctorhouse.domain.port.out;

import com.example.doctorhouse.domain.model.User;

public interface TokenProviderPort {
    String generateToken(User user);
}
