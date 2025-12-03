package com.example.smart_fitness.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.smart_fitness.Members.Member;
import com.example.smart_fitness.Members.MembershipPlan;
import com.example.smart_fitness.ServiceLayer.ServiceInterface;
import com.example.smart_fitness.Trainers.CardioTrainer;
import com.example.smart_fitness.Trainers.StrengthTrainer;
import com.example.smart_fitness.Trainers.Trainer;
import com.example.smart_fitness.Trainers.YogaTrainer;
import com.example.smart_fitness.WorkoutSession.WorkoutSession;

@RestController
@RequestMapping("/smartfitness")
@Profile("mock")
public class InMemoryController {

  private ServiceInterface workoutSessions;

  public InMemoryController(ServiceInterface workoutSessions) {
    this.workoutSessions = workoutSessions;
  }

  @PostMapping("/register/trainer")
  public Trainer registerTrainer(@RequestBody TrainerRecord record) {
    Trainer trainer;
    switch (record.specialization()) {
      case "Yoga" -> {
        trainer = new YogaTrainer(record.id(), record.name(), record.experienceYears());
      }
      case "Strength Training" -> {
        trainer = new StrengthTrainer(record.id(), record.name(), record.experienceYears());
      }
      case "Cardio" -> {
        trainer = new CardioTrainer(record.id(), record.name(), record.experienceYears());
      }
      default -> trainer = null;
    }

    return workoutSessions.registerTrainer(trainer);
  }

  @PostMapping("/register/member")
  public Member registerMember(@RequestBody MemberRecord record) {
    Member member = new Member(record.id(), record.name(), record.age(), record.membershipPlan());

    return workoutSessions.registerMember(member);
  }

  @PostMapping("/register/session")
  public WorkoutSession registerSession(@RequestBody SessionRecord record) {

    return workoutSessions.createWorkoutSession(record.memberId(), record.trainerId(), record.date(), record.duration(),
        record.caloriesBurn());

  }

  @GetMapping("/trainers")
  public List<Trainer> getAllTrainers() {
    return workoutSessions.getAllTrainer();
  }

  @GetMapping("/trainers/{id}")
  public Trainer getAllTrainers(@PathVariable("id") int id) {
    return workoutSessions.getTrainerById(id);
  }

  @GetMapping("/members/{id}/averageCalories")
  public double getAverageCaloriesForMember(@PathVariable("id") int id) {
    return workoutSessions.getAverageCaloriesForMember(id);
  }

  @GetMapping("/trainers/mostActive")
  public Trainer getMostActiveTrainer() {
    return workoutSessions.getMostActiveTrainer();
  }

  @GetMapping("/trainers/revenue")
  public Map<String, Double> getRevenueTop() {
    return workoutSessions.getRevenuePerTrainer();
  }

  @GetMapping("/members/top/{limit}")
  public List<Member> getMemberCals(@PathVariable("limit") int limit) {
    return workoutSessions.getTopMembersByCalories(limit);
  }

  @GetMapping("/members/plan")
  public Map<MembershipPlan, List<Member>> getMemberCals() {
    return workoutSessions.groupMembersByPlan();
  }
}

record TrainerRecord(int id, String name, int experienceYears, String specialization) {
}

record MemberRecord(int id, String name, int age, MembershipPlan membershipPlan) {
}

record WorkoutSessionRecord(int sessionId, LocalDate date, int duration,
    int caloriesBurned, Trainer trainer, Member member) {
}

record SessionRecord(
    int memberId,
    int trainerId,
    String date,
    int duration,
    int caloriesBurn) {
}