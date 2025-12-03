package com.example.smart_fitness.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.smart_fitness.WorkoutSession.WorkoutSession;

public interface WorkoutSessionRepo extends JpaRepository<WorkoutSession, Integer> {

 List<WorkoutSession> findByMemberId(int memberId);

 List<WorkoutSession> findByDateBetween(LocalDate start, LocalDate end);

}
