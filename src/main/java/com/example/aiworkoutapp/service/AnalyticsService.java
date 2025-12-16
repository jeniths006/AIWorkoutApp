package com.example.aiworkoutapp.service;

import com.example.aiworkoutapp.dto.analytics.ExerciseProgressionResponse;
import com.example.aiworkoutapp.dto.analytics.PrsResponse;
import com.example.aiworkoutapp.dto.analytics.WeeklyVolumeResponse;
import com.example.aiworkoutapp.dto.analytics.WorkoutSummaryResponse;
import com.example.aiworkoutapp.repository.AnalyticsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AnalyticsService {

    private final AnalyticsRepository analyticsRepository;

    public AnalyticsService(AnalyticsRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }

    public WorkoutSummaryResponse workoutSummary(UUID workoutId) {
        var totals = analyticsRepository.workoutTotals(workoutId);
        if (totals == null) throw new IllegalArgumentException("Workout not found: " + workoutId);

        var breakdown = analyticsRepository.workoutExerciseBreakdown(workoutId).stream()
                .map(r -> new WorkoutSummaryResponse.ExerciseBreakdown(
                        r.getExerciseId(),
                        r.getExerciseName(),
                        r.getSets(),
                        r.getReps(),
                        d(r.getVolume())
                ))
                .toList();

        return new WorkoutSummaryResponse(
                totals.getWorkoutId(),
                odt(totals.getWorkoutDate()),
                totals.getTotalSets(),
                totals.getTotalReps(),
                d(totals.getTotalVolume()),
                breakdown
        );
    }

    public WeeklyVolumeResponse weeklyVolume(UUID userId, int weeks) {
        int w = clamp(weeks, 1, 52);
        var points = analyticsRepository.weeklyVolume(userId, w).stream()
                .map(r -> new WeeklyVolumeResponse.WeekPoint(
                        odt(r.getWeekStart()), d(r.getVolume()), r.getSets(), r.getReps()
                ))
                .toList();
        return new WeeklyVolumeResponse(points);
    }

    public ExerciseProgressionResponse exerciseProgression(UUID userId, UUID exerciseId, String metric, int rangeDays) {
        int days = clamp(rangeDays, 7, 3650);

        String exerciseName = analyticsRepository.exerciseName(exerciseId);
        if (exerciseName == null) {
            throw new IllegalArgumentException("Exercise not found: " + exerciseId);
        }

        List<AnalyticsRepository.TimeSeriesPointRow> rows = switch (metric) {
            case "top_weight" -> analyticsRepository.topWeightSeries(userId, exerciseId, days);
            case "volume" -> analyticsRepository.volumeSeries(userId, exerciseId, days);
            case "e1rm" -> analyticsRepository.e1rmSeries(userId, exerciseId, days);
            default -> throw new IllegalArgumentException("Unknown metric: " + metric + " (use top_weight, volume, e1rm)");
        };

        var points = rows.stream()
                .map(r -> new ExerciseProgressionResponse.Point(odt(r.getDate()), d(r.getValue())))
                .toList();

        return new ExerciseProgressionResponse(exerciseId, exerciseName, metric, points);
    }

    public PrsResponse prs(UUID userId, int limit) {
        int lim = clamp(limit, 1, 200);

        var prs = analyticsRepository.bestE1rmPerExercise(userId).stream()
                .sorted(Comparator.comparingDouble(r -> -d(r.getE1rm())))
                .limit(lim)
                .map(r -> new PrsResponse.PrItem(
                        r.getExerciseId(),
                        r.getExerciseName(),
                        "E1RM",
                        d(r.getE1rm()),
                        d(r.getWeight()),
                        r.getReps() == null ? 0 : r.getReps(),
                        r.getWorkoutId(),
                        odt(r.getWorkoutDate())
                ))
                .toList();

        return new PrsResponse(prs);
    }

    private static double d(Double v) { return v == null ? 0.0 : v; }
    private static int clamp(int v, int min, int max) { return Math.max(min, Math.min(max, v)); }

    private static java.time.OffsetDateTime odt(java.time.Instant i) {
        return i == null ? null : java.time.OffsetDateTime.ofInstant(i, java.time.ZoneOffset.UTC);
    }



}
