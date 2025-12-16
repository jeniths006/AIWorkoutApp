package com.example.aiworkoutapp.service;

import com.example.aiworkoutapp.dto.CreateWorkoutRequest;
import com.example.aiworkoutapp.dto.WorkoutResponse;
import com.example.aiworkoutapp.entity.WorkoutEntity;
import com.example.aiworkoutapp.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    private static final UUID DEV_USER_ID =
            UUID.fromString("00000000-0000-0000-0000-000000000001");

    public WorkoutResponse create(CreateWorkoutRequest req) {
        var now = OffsetDateTime.now();

        var workout = WorkoutEntity.builder()
                .id(UUID.randomUUID())
                .userId(DEV_USER_ID)
                .name(req.name())
                .workoutDate(now)
                .createdAt(now)
                .build();

        var saved = workoutRepository.save(workout);
        return new WorkoutResponse(saved.getId(), saved.getName(), saved.getWorkoutDate());
    }

    public List<WorkoutResponse> listMine() {
        return workoutRepository.findByUserIdOrderByWorkoutDateDesc(DEV_USER_ID)
                .stream()
                .map(w -> new WorkoutResponse(w.getId(), w.getName(), w.getWorkoutDate()))
                .toList();
    }









}
