package com.example.smart_fitness.Profiles_Configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.smart_fitness.Members.Member;
import com.example.smart_fitness.Trainers.Trainer;
import com.example.smart_fitness.WorkoutSession.WorkoutSession;

@Configuration
public class FitnessConfig {

    @Bean
    public List<Trainer> trainerList() {
        return new ArrayList<>();
    }

    @Bean
    public List<Member> memberList() {
        return new ArrayList<>();
    }

    @Bean
    public List<WorkoutSession> sessionList() {
        return new ArrayList<>();
    }
}
