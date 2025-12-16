package com.example.aiworkoutapp.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record WorkoutResponse(UUID id, String name, OffsetDateTime workoutDate) {
}
