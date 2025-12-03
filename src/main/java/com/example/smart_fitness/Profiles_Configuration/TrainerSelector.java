package com.example.smart_fitness.Profiles_Configuration;

import com.example.smart_fitness.Trainers.Trainer;

public interface TrainerSelector {
 Trainer assignTrainer();
}
/*
 * Profile 1: @Profile("dev")
 * 
 * Always return a YogaTrainer
 * 
 * Profile 2: @Profile("prod")
 * 
 * Select trainer based on:
 * 
 * availability
 * 
 * lowest workload (use streams)
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * @Configuration
 * public class TrainerConfig {
 * 
 * @Bean
 * 
 * @Profile("dev")
 * public TrainerSelector devSelector() { ... }
 * 
 * @Bean
 * 
 * @Profile("prod")
 * public TrainerSelector prodSelector() { ... }
 * }
 * 
 */