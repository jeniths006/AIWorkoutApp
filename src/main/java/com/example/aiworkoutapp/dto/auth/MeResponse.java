package com.example.aiworkoutapp.dto.auth;

import java.util.UUID;

public record MeResponse(
        UUID userId,
        String email
) {

}
