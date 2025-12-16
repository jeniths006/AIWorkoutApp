package com.example.aiworkoutapp.dto.analytics;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record ExerciseProgressionResponse(
        UUID exerciseId,
        String exerciseName,
        String metric,
        List<Point> points
) {
    public record Point(OffsetDateTime date, double value) {}
}
