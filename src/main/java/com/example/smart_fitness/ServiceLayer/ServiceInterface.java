package com.example.smart_fitness.ServiceLayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.smart_fitness.Members.Member;
import com.example.smart_fitness.Members.MembershipPlan;
import com.example.smart_fitness.Trainers.Trainer;
import com.example.smart_fitness.WorkoutSession.WorkoutSession;

public interface ServiceInterface {
 Trainer registerTrainer(Trainer trainer);

 Member registerMember(Member member);

 List<Trainer> getAllTrainer();

 WorkoutSession createWorkoutSession(int memberId, int trainerId, String date, int duration, int caloriesBurn);

 List<Member> getTopMembersByCalories(int limit);

 double getAverageCaloriesForMember(int memberId);

 Trainer getTrainerById(int id);

 Map<String, Double> getRevenuePerTrainer();

 List<WorkoutSession> getSessionsBetween(String startDate, String endDate);

 Trainer getMostActiveTrainer();

 Map<MembershipPlan, List<Member>> groupMembersByPlan();

 Trainer getBestPerformanceTrainer();

 Map<String, Integer> getCaloriesGroupedBySpecialization();

 List<WorkoutSession> sortSessionsByDuration();

 HashMap<String, Object> getMemberProgressSummary(int memberId);

}
