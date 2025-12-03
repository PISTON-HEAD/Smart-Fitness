package com.example.smart_fitness.ServiceLayer;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.example.smart_fitness.Members.Member;
import com.example.smart_fitness.Members.MembershipPlan;
import com.example.smart_fitness.Repository.MemberRepo;
import com.example.smart_fitness.Repository.TrainerRepo;
import com.example.smart_fitness.Repository.WorkoutSessionRepo;
import com.example.smart_fitness.Trainers.Trainer;
import com.example.smart_fitness.WorkoutSession.WorkoutSession;

@Service
@Profile("jpa")
public class DbService implements ServiceInterface {

 private final TrainerRepo trainerRepository;
 private final MemberRepo memberRepository;
 private final WorkoutSessionRepo workoutSessionRepo;

 public DbService(TrainerRepo trainerRepo, MemberRepo memberRepo, WorkoutSessionRepo workoutSessionRepo) {
  this.trainerRepository = trainerRepo;
  this.memberRepository = memberRepo;
  this.workoutSessionRepo = workoutSessionRepo;
 }

 @Override
 public Trainer registerTrainer(Trainer trainer) {
  if (trainerRepository.existsById(trainer.getId())) {
   return null;
  }
  return trainerRepository.save(trainer);
 }

 @Override
 public Member registerMember(Member member) {
  if (!memberRepository.existsById(member.getId())) {
   return memberRepository.save(member);
  }
  return null;
 }

 @Override
 public List<Trainer> getAllTrainer() {
  return trainerRepository.findAll();
 }

 @Override
 public WorkoutSession createWorkoutSession(int memberId, int trainerId, String date, int duration, int caloriesBurn) {
  Member member = memberRepository.findById(memberId).orElse(null);
  Trainer trainer = trainerRepository.findById(trainerId).orElse(null);
  if (member == null || trainer == null)
   return null;

  WorkoutSession session = new WorkoutSession();
  session.setDate(LocalDate.parse(date));
  session.setDuration(duration);
  session.setCaloriesBurned(caloriesBurn);
  session.setTrainer(trainer);
  session.setMember(member);

  member.getSessions().add(session);
  return workoutSessionRepo.save(session);
 }

 @Override
 public List<Member> getTopMembersByCalories(int limit) {
  return workoutSessionRepo.findAll().stream().sorted(Comparator.comparing(WorkoutSession::getCaloriesBurned))
    .map(WorkoutSession::getMember).distinct().limit(limit).toList();
 }

 @Override
 public double getAverageCaloriesForMember(int memberId) {
  return workoutSessionRepo.findAll().stream().filter(i -> i.getMember().getId() == memberId)
    .mapToInt(i -> i.getCaloriesBurned()).average().orElse(0.0);
 }

 @Override
 public Trainer getTrainerById(int id) {
  return trainerRepository.findById(id).orElse(null);
 }

 @Override
 public Map<String, Double> getRevenuePerTrainer() {
  return workoutSessionRepo.findAll().stream()
    .collect(Collectors.groupingBy(i -> i.getTrainer().getName(), Collectors.summingDouble(i -> i.getSessionCost())));
 }

 @Override
 public List<WorkoutSession> getSessionsBetween(String startDate, String endDate) {
  LocalDate start = LocalDate.parse(startDate);
  LocalDate end = LocalDate.parse(endDate);
  return workoutSessionRepo.findByDateBetween(start, end);
 }

 @Override
 public Trainer getMostActiveTrainer() {
  return workoutSessionRepo.findAll()
    .stream()
    .collect(
      Collectors.groupingBy(WorkoutSession::getTrainer, Collectors.summingDouble(WorkoutSession::getSessionCost)))
    .entrySet()
    .stream()
    .max(Map.Entry.comparingByValue())
    .map(Map.Entry::getKey)
    .orElse(null);
 }

 @Override
 public Map<MembershipPlan, List<Member>> groupMembersByPlan() {
  return memberRepository.findAll()
    .stream()
    .collect(Collectors.groupingBy(Member::getMembershipPlan));
 }

 @Override
 public Trainer getBestPerformanceTrainer() {
  return workoutSessionRepo.findAll()
    .stream()
    .collect(Collectors.groupingBy(WorkoutSession::getTrainer))
    .entrySet()
    .stream()
    .max((e1, e2) -> Integer.compare(e1.getValue().size(), e2.getValue().size()))
    .map(Map.Entry::getKey)
    .orElse(null);
 }

 @Override
 public Map<String, Integer> getCaloriesGroupedBySpecialization() {
  return workoutSessionRepo.findAll()
    .stream()
    .collect(Collectors.groupingBy(
      s -> s.getTrainer().getSpecialization(),
      Collectors.summingInt(WorkoutSession::getCaloriesBurned)));
 }

 @Override
 public List<WorkoutSession> sortSessionsByDuration() {
  return workoutSessionRepo.findAll()
    .stream()
    .sorted(Comparator.comparingInt(WorkoutSession::getDuration))
    .toList();
 }

 @Override
 public HashMap<String, Object> getMemberProgressSummary(int memberId) {
  List<WorkoutSession> sessions = workoutSessionRepo.findByMemberId(memberId);
  HashMap<String, Object> summary = new HashMap<>();
  summary.put("totalSessions", sessions.size());
  summary.put("totalCalories", sessions.stream().mapToInt(WorkoutSession::getCaloriesBurned).sum());
  summary.put("averageCalories", sessions.stream().mapToInt(WorkoutSession::getCaloriesBurned).average().orElse(0));
  summary.put("totalDuration", sessions.stream().mapToInt(WorkoutSession::getDuration).sum());
  return summary;
 }

}
