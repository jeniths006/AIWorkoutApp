package com.example.aiworkoutapp.controller;

import com.example.aiworkoutapp.dto.analytics.ExerciseProgressionResponse;
import com.example.aiworkoutapp.dto.analytics.PrsResponse;
import com.example.aiworkoutapp.dto.analytics.WeeklyVolumeResponse;
import com.example.aiworkoutapp.dto.analytics.WorkoutSummaryResponse;
import com.example.aiworkoutapp.service.AnalyticsService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/workouts/{workoutId}/summary")
    public WorkoutSummaryResponse workoutSummary(@PathVariable UUID workoutId) {
        return analyticsService.workoutSummary(workoutId);
    }

    @GetMapping("/volume/weekly")
    public WeeklyVolumeResponse weeklyVolume(
            @RequestParam UUID userId,
            @RequestParam(defaultValue = "12") int weeks
    ) {
        return analyticsService.weeklyVolume(userId, weeks);
    }

    @GetMapping("/exercises/{exerciseId}/progression")
    public ExerciseProgressionResponse exerciseProgression(
            @PathVariable UUID exerciseId,
            @RequestParam UUID userId,
            @RequestParam(defaultValue = "e1rm") String metric,
            @RequestParam(defaultValue = "180") int rangeDays
    ) {
        return analyticsService.exerciseProgression(userId, exerciseId, metric, rangeDays);
    }

    @GetMapping("/prs")
    public PrsResponse prs(
            @RequestParam UUID userId,
            @RequestParam(defaultValue = "50") int limit
    ) {
        return analyticsService.prs(userId, limit);
    }
}
