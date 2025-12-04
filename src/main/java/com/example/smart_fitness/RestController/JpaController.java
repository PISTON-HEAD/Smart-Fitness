package com.example.smart_fitness.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.smart_fitness.Members.Member;
import com.example.smart_fitness.Members.MembershipPlan;
import com.example.smart_fitness.Records_DTO.WorkoutSessionMapper;
import com.example.smart_fitness.Records_DTO.WorkoutSessionMiniDTO;
import com.example.smart_fitness.ServiceLayer.ServiceInterface;
import com.example.smart_fitness.Trainers.CardioTrainer;
import com.example.smart_fitness.Trainers.StrengthTrainer;
import com.example.smart_fitness.Trainers.Trainer;
import com.example.smart_fitness.Trainers.YogaTrainer;
import com.example.smart_fitness.WorkoutSession.WorkoutSession;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@RestController
@RequestMapping("/smartfitness")
@Profile("jpa")
public class JpaController {

  private ServiceInterface service;

  public JpaController(ServiceInterface service) {
    this.service = service;
  }

  // ===========================
  // TRAINER REGISTRATION
  // ===========================
  @PostMapping("/register/trainer")
  public Trainer registerTrainer(@Valid @RequestBody TrainerRequestJpa record) {
    Trainer trainer;

    switch (record.specialization()) {
      case "Yoga" -> trainer = new YogaTrainer(record.name(), record.experienceYears());
      case "Strength Training" -> trainer = new StrengthTrainer(record.name(), record.experienceYears());
      case "Cardio" -> trainer = new CardioTrainer(record.name(), record.experienceYears());
      default -> throw new IllegalArgumentException("Unsupported specialization");
    }

    return service.registerTrainer(trainer);
  }

  // ===========================
  // MEMBER REGISTRATION
  // ===========================
  @PostMapping("/register/member")
  public Member registerMember(@Valid @RequestBody MemberRequestJpa record) {
    // No ID passed — JPA will generate it
    Member member = new Member(
        record.name(),
        record.age(),
        record.membershipPlan());
    return service.registerMember(member);
  }

  // ===========================
  // CREATE WORKOUT SESSION
  // ===========================
  @PostMapping("/register/session")
  public WorkoutSessionMiniDTO registerSession(@Valid @RequestBody SessionRecord record) {
    WorkoutSession session = service.createWorkoutSession(
        record.memberId(),
        record.trainerId(),
        record.date(),
        record.duration(),
        record.caloriesBurn());
    // WorkoutSessionMapper WorkoutSessionMapper = new WorkoutSessionMapper(); -> if
    // non static
    return WorkoutSessionMapper.toMiniDTO(session);

  }

  // ===========================
  // GET TRAINERS
  // ===========================
  @GetMapping("/trainers")
  public List<Trainer> getAllTrainers() {
    return service.getAllTrainer();
  }

  @GetMapping("/trainers/{id}")
  public Trainer getTrainerById(@PathVariable int id) {
    return service.getTrainerById(id);
  }

  // ===========================
  // MEMBER STATS
  // ===========================
  @GetMapping("/members/{id}/averageCalories")
  public double getAverageCaloriesForMember(@PathVariable int id) {
    return service.getAverageCaloriesForMember(id);
  }

  // ===========================
  // TRAINER STATS
  // ===========================
  @GetMapping("/trainers/mostActive")
  public Trainer getMostActiveTrainer() {
    return service.getMostActiveTrainer();
  }

  @GetMapping("/trainers/revenue")
  public Map<String, Double> getRevenueTop() {
    return service.getRevenuePerTrainer();
  }

  // ===========================
  // TOP MEMBERS
  // ===========================
  @GetMapping("/members/top/{limit}")
  public List<Member> getTopMembers(@PathVariable int limit) {
    return service.getTopMembersByCalories(limit);
  }

  // ===========================
  // GROUP MEMBERS BY PLAN
  // ===========================
  @GetMapping("/members/plan")
  public Map<MembershipPlan, List<Member>> groupMembersByPlan() {
    return service.groupMembersByPlan();

  }

  @GetMapping("/members/progress/{id}")
  public HashMap<String, Object> groupMembersByPlan(@PathVariable int id) {
    return service.getMemberProgressSummary(id);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleMethodArgumentValidation(MethodArgumentNotValidException exp) {
    Map<String, List<String>> errors = exp.getBindingResult()
        .getFieldErrors()
        .stream()
        .collect(Collectors.groupingBy(
            FieldError::getField,
            Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

  }
}

record TrainerRequestJpa(
    @NotEmpty @Size(min = 3, message = "name must be at least 3 characters") String name,
    int experienceYears,
    String specialization) {
}

record MemberRequestJpa(
    @NotEmpty @Size(min = 3, message = "name must be at least 3 characters") String name,
    int age,
    MembershipPlan membershipPlan) {
}

record SessionRecord(
    @Min(1) int memberId,
    @Min(1) int trainerId,
    String date,
    int duration,
    int caloriesBurn) {
}
