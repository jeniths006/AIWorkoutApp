package com.example.aiworkoutapp.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateWorkoutRequest(@NotBlank String name) {
}
