package com.example.smart_fitness.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.smart_fitness.Trainers.Trainer;

public interface TrainerRepo extends JpaRepository<Trainer, Integer> {

}
