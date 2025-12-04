package com.example.smart_fitness.Records_DTO;

public record WorkoutSessionMiniDTO(
  int sessionId,
  int duration,
  String trainerName) {
}