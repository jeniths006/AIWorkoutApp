package com.example.aiworkoutapp.repository;

import com.example.aiworkoutapp.entity.WorkoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkoutRepository extends JpaRepository<WorkoutEntity, UUID> {
    List<WorkoutEntity> findByUserIdOrderByWorkoutDateDesc(UUID workoutId);

}
