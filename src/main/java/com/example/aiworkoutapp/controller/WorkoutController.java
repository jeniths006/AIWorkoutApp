package com.example.aiworkoutapp.controller;


import com.example.aiworkoutapp.dto.CreateWorkoutRequest;
import com.example.aiworkoutapp.dto.WorkoutResponse;
import com.example.aiworkoutapp.service.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @PostMapping
    public WorkoutResponse create(@Valid @RequestBody CreateWorkoutRequest req) {
        return workoutService.create(req);
    }

    @GetMapping
    public List<WorkoutResponse> list() {
        return workoutService.listMine();
    }
}
