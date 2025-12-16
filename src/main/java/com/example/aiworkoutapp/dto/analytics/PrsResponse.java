package com.example.aiworkoutapp.dto.analytics;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record PrsResponse(List<PrItem> prs) {
    public record PrItem(
            UUID exerciseId,
            String exerciseName,
            String type,
            double value,
            double weight,
            int reps,
            UUID workoutId,
            OffsetDateTime workoutDate
    ) {}
}
