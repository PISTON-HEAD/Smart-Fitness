package com.example.smart_fitness.Records_DTO;

import com.example.smart_fitness.WorkoutSession.WorkoutSession;

public class WorkoutSessionMapper {

 public static WorkoutSessionMiniDTO toMiniDTO(WorkoutSession session) {
  return new WorkoutSessionMiniDTO(
    session.getSessionId(),
    session.getDuration(),
    session.getTrainer().getName());
 }
}
