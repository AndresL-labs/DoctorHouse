package com.example.doctorhouse.domain.port.out;

import java.util.Optional;

public interface SecurityPort {
    Optional<Long> getAuthenticatedUserId();

    String getAuthenticatedUserRole();
}
