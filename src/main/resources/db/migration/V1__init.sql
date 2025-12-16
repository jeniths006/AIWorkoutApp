CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       email TEXT UNIQUE,
                       name TEXT,
                       created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE workouts (
                          id UUID PRIMARY KEY,
                          user_id UUID NOT NULL REFERENCES users(id),
                          name TEXT NOT NULL,
                          workout_date TIMESTAMPTZ NOT NULL DEFAULT now(),
                          created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE exercises (
                           id UUID PRIMARY KEY,
                           name TEXT NOT NULL UNIQUE,
                           created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE workout_exercises (
                                   id UUID PRIMARY KEY,
                                   workout_id UUID NOT NULL REFERENCES workouts(id) ON DELETE CASCADE,
                                   exercise_id UUID NOT NULL REFERENCES exercises(id),
                                   sort_order INT NOT NULL DEFAULT 0,
                                   created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE sets (
                      id UUID PRIMARY KEY,
                      workout_exercise_id UUID NOT NULL REFERENCES workout_exercises(id) ON DELETE CASCADE,
                      weight DOUBLE PRECISION NOT NULL,
                      reps INT NOT NULL,
                      rpe DOUBLE PRECISION,
                      created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
