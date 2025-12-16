package com.example.aiworkoutapp.repository;

import com.example.aiworkoutapp.entity.WorkoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface AnalyticsRepository extends JpaRepository<WorkoutEntity, UUID> {

    interface WorkoutTotalsRow {
        UUID getWorkoutId();
        java.time.Instant getWorkoutDate();
        long getTotalSets();
        long getTotalReps();
        Double getTotalVolume();
    }

    interface ExerciseBreakdownRow {
        UUID getExerciseId();
        String getExerciseName();
        long getSets();
        long getReps();
        Double getVolume();
    }

    interface WeeklyVolumeRow {
        java.time.Instant getWeekStart();
        Double getVolume();
        long getSets();
        long getReps();
    }

    interface TimeSeriesPointRow {
        java.time.Instant getDate();
        Double getValue();
    }

    interface ExercisePrRow {
        UUID getExerciseId();
        String getExerciseName();
        Double getE1rm();
        Double getWeight();
        Integer getReps();
        UUID getWorkoutId();
        java.time.Instant getWorkoutDate();
    }

    @Query(value = "SELECT name FROM exercises WHERE id = :exerciseId", nativeQuery = true)
    String exerciseName(@Param("exerciseId") UUID exerciseId);


    @Query(value = """
            SELECT
                w.id AS workout_id,
                w.workout_date AS workout_date,
                COUNT(s.id) AS total_sets,
                COALESCE(SUM(s.reps), 0) AS total_reps,
                COALESCE(SUM(s.weight * s.reps), 0) AS total_volume
            FROM workouts w
            JOIN workout_exercises we On we.workout_id = w.id
            JOIN sets s ON s.workout_exercise_id = we.id
            WHERE w.id = :workoutId
            GROUP BY w.id, w.workout_date
            """, nativeQuery = true)
    WorkoutTotalsRow workoutTotals(@Param("workoutId") UUID workoutId);

    @Query(value = """
        SELECT
                e.id AS exercise_id,
                e.name AS exercise_name,
                COUNT(s.id) AS sets,
                COALESCE(SUM(s.reps), 0) AS reps,
                COALESCE(SUM(s.weight * s.reps), 0) AS volume
        FROM workouts w
        JOIN workout_exercises we On we.workout_id = w.id
        JOIN exercises e On e.id = we.exercise_id
        JOIN sets s ON s.workout_Exercise_id = we.id
        WHERE w.id = :workoutId
        GROUP BY e.id, e.name
        ORDER BY volume DESC 
        """, nativeQuery = true)
    List<ExerciseBreakdownRow> workoutExerciseBreakdown(@Param("workoutId") UUID workoutId);

    @Query(value = """
        SELECT
          date_trunc('week', w.workout_date) AS week_start,
          COALESCE(SUM(s.weight * s.reps), 0) AS volume,
          COUNT(s.id) AS sets,
          COALESCE(SUM(s.reps), 0) AS reps
        FROM workouts w
        JOIN workout_exercises we ON we.workout_id = w.id
        JOIN sets s ON s.workout_exercise_id = we.id
        WHERE w.user_id = :userId
          AND w.workout_date >= (NOW() - (:weeks * INTERVAL '7 days'))
        GROUP BY week_start
        ORDER BY week_start
        """, nativeQuery = true)
    List<WeeklyVolumeRow> weeklyVolume(@Param("userId") UUID userId, @Param("weeks") int weeks);

    @Query(value = """
        SELECT
          date_trunc('day', w.workout_date) AS date,
          MAX(s.weight) AS value
        FROM workouts w
        JOIN workout_exercises we ON we.workout_id = w.id
        JOIN sets s ON s.workout_exercise_id = we.id
        WHERE w.user_id = :userId
          AND we.exercise_id = :exerciseId
          AND w.workout_date >= (NOW() - (:rangeDays * INTERVAL '1 day'))
        GROUP BY date
        ORDER BY date
        """, nativeQuery = true)
    List<TimeSeriesPointRow> topWeightSeries(@Param("userId") UUID userId,
                                             @Param("exerciseId") UUID exerciseId,
                                             @Param("rangeDays") int rangeDays);

    @Query(value = """
        SELECT
          date_trunc('day', w.workout_date) AS date,
          COALESCE(SUM(s.weight * s.reps), 0) AS value
        FROM workouts w
        JOIN workout_exercises we ON we.workout_id = w.id
        JOIN sets s ON s.workout_exercise_id = we.id
        WHERE w.user_id = :userId
          AND we.exercise_id = :exerciseId
          AND w.workout_date >= (NOW() - (:rangeDays * INTERVAL '1 day'))
        GROUP BY date
        ORDER BY date
        """, nativeQuery = true)
    List<TimeSeriesPointRow> volumeSeries(@Param("userId") UUID userId,
                                          @Param("exerciseId") UUID exerciseId,
                                          @Param("rangeDays") int rangeDays);

    @Query(value = """
        SELECT
          date_trunc('day', w.workout_date) AS date,
          MAX(s.weight * (1 + (s.reps / 30.0))) AS value
        FROM workouts w
        JOIN workout_exercises we ON we.workout_id = w.id
        JOIN sets s ON s.workout_exercise_id = we.id
        WHERE w.user_id = :userId
          AND we.exercise_id = :exerciseId
          AND w.workout_date >= (NOW() - (:rangeDays * INTERVAL '1 day'))
        GROUP BY date
        ORDER BY date
        """, nativeQuery = true)
    List<TimeSeriesPointRow> e1rmSeries(@Param("userId") UUID userId,
                                        @Param("exerciseId") UUID exerciseId,
                                        @Param("rangeDays") int rangeDays);

    @Query(value = """
        SELECT DISTINCT ON (e.id)
          e.id AS exercise_id,
          e.name AS exercise_name,
          (s.weight * (1 + (s.reps / 30.0))) AS e1rm,
          s.weight AS weight,
          s.reps AS reps,
          w.id AS workout_id,
          w.workout_date AS workout_date
        FROM workouts w
        JOIN workout_exercises we ON we.workout_id = w.id
        JOIN exercises e ON e.id = we.exercise_id
        JOIN sets s ON s.workout_exercise_id = we.id
        WHERE w.user_id = :userId
        ORDER BY e.id, e1rm DESC, w.workout_date DESC
        """, nativeQuery = true)
    List<ExercisePrRow> bestE1rmPerExercise(@Param("userId") UUID userId);
}
