package com.example.aiworkoutapp.dto.analytics;

import java.time.OffsetDateTime;
import java.util.*;

public record WorkoutSummaryResponse(
        UUID workoutId,
        OffsetDateTime workoutDate,
        long totalSets,
        long totalReps,
        double totalVolume,
        List<ExerciseBreakdown> exerciseBreakdown
) {
    public record ExerciseBreakdown(
            UUID exerciseId,
            String exerciseName,
            long sets,
            long reps,
            double volume
    ) {}
}
