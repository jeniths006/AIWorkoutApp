CREATE INDEX IF NOT EXISTS idx_workouts_user_date
ON workouts (user_id, workout_date);

CREATE INDEX IF NOT EXISTS idx_workout_exercises_workout
ON workout_exercises (workout_id);

CREATE INDEX IF NOT EXISTS idx_workout_exercises_exercise
ON workout_exercises (exercise_id);

CREATE INDEX IF NOT EXISTS idx_sets_workout_exercise
ON sets (workout_exercise_id)