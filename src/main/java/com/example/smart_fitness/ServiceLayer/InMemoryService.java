package com.example.smart_fitness.ServiceLayer;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.example.smart_fitness.Members.Member;
import com.example.smart_fitness.Members.MembershipPlan;
import com.example.smart_fitness.Trainers.Trainer;
import com.example.smart_fitness.WorkoutSession.WorkoutSession;

@Service
@Profile("mock")
@Primary
public class InMemoryService implements ServiceInterface {

  List<Trainer> trainers;
  List<WorkoutSession> workoutSessions;
  List<Member> members;

  public InMemoryService(List<Trainer> trainers, List<WorkoutSession> workoutSessions, List<Member> members) {
    this.trainers = trainers;
    this.workoutSessions = workoutSessions;
    this.members = members;
  }

  @Override
  public Trainer registerTrainer(Trainer trainer) {
    if (verifyIfTrainer(trainer) == null) {
      trainers.add(trainer);
      return trainer;
    }
    return null;
  }

  public Trainer verifyIfTrainer(Trainer t) {
    Trainer trainer = trainers.stream().filter(i -> i.getId() == t.getId()).findAny().orElse(null);
    return trainer;
  }

  public Trainer verifyIfTrainer(int t) {
    Trainer trainer = trainers.stream().filter(i -> i.getId() == t).findAny().orElse(null);
    return trainer;
  }

  @Override
  public Member registerMember(Member member) {
    if (verifyIfMember(member) == null) {
      members.add(member);
      return member;
    }
    return null;
  }

  public Member verifyIfMember(Member t) {
    Member trainer = members.stream().filter(i -> i.getId() == t.getId()).findAny().orElse(null);
    return trainer;
  }

  public Member verifyIfMember(int t) {
    Member trainer = members.stream().filter(i -> i.getId() == t).findAny().orElse(null);
    return trainer;
  }

  @Override
  public WorkoutSession createWorkoutSession(int memberId, int trainerId, String date, int duration, int caloriesBurn) {
    Trainer trainer = verifyIfTrainer(trainerId);
    Member member = verifyIfMember(memberId);

    if (trainer == null || member == null) {
      return null;
    }
    WorkoutSession session = new WorkoutSession(1000 + workoutSessions.size(), LocalDate.now(), duration, caloriesBurn,
        trainer, member);
    workoutSessions
        .add(session);
    member.addSession(session);
    return session;
  }

  @Override
  public List<Member> getTopMembersByCalories(int limit) {
    Collections.sort(workoutSessions,
        (WorkoutSession w1, WorkoutSession w2) -> Integer.compare(w2.getCaloriesBurned(), w1.getCaloriesBurned()));
    List<Member> topMembers = workoutSessions.stream().map(i -> i.getMember()).limit(limit).toList();
    return topMembers;
  }

  @Override
  public double getAverageCaloriesForMember(int memberId) {
    return workoutSessions.stream().filter(i -> i.getMember().getId() == memberId)
        .mapToInt(WorkoutSession::getCaloriesBurned).average().orElse(0.0);
  }

  @Override
  public Map<String, Double> getRevenuePerTrainer() {
    return workoutSessions.stream().collect(
        Collectors.groupingBy(i -> i.getTrainer().getName(), Collectors.summingDouble(i -> i.getSessionCost())));
  }

  @Override
  public List<WorkoutSession> getSessionsBetween(String startDate, String endDate) {
    LocalDate start = LocalDate.parse(startDate);
    LocalDate end = LocalDate.parse(endDate);

    return workoutSessions.stream()
        .filter(session -> !session.getDate().isBefore(start) &&
            !session.getDate().isAfter(end))
        .toList();
  }

  @Override
  public Trainer getMostActiveTrainer() {
    return workoutSessions.stream()
        .collect(Collectors.groupingBy(
            WorkoutSession::getTrainer,
            Collectors.summingDouble(WorkoutSession::getSessionCost)))
        .entrySet()
        .stream()
        .max(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey)
        .orElse(null);
  }

  @Override
  public Map<MembershipPlan, List<Member>> groupMembersByPlan() {
    return workoutSessions.stream().map(WorkoutSession::getMember).distinct()
        .collect(Collectors.groupingBy(i -> i.getMembershipPlan()));

  }

  @Override
  public Trainer getBestPerformanceTrainer() {

    return workoutSessions.stream().collect(Collectors.groupingBy(i -> i.getTrainer())).entrySet().stream()
        .max(Comparator.comparing(i -> i.getValue().size())).map(i -> i.getKey()).orElse(null);
  }

  @Override
  public Map<String, Integer> getCaloriesGroupedBySpecialization() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public List<WorkoutSession> sortSessionsByDuration() {
    Collections.sort(workoutSessions, Comparator.comparing(WorkoutSession::getDuration));
    return workoutSessions;
  }

  @Override
  public HashMap<String, Object> getMemberProgressSummary(int memberId) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public List<Trainer> getAllTrainer() {
    return trainers;
  }

  @Override
  public Trainer getTrainerById(int id) {
    return verifyIfTrainer(id);
  }

}
