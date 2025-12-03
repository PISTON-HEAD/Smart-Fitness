package com.example.smart_fitness.WorkoutSession;

import java.time.LocalDate;

import com.example.smart_fitness.Members.Member;
import com.example.smart_fitness.Trainers.Trainer;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Entity
public class WorkoutSession {

  public WorkoutSession() {
  }

  @Id
  @GeneratedValue
  private int sessionId;
  private LocalDate date;
  private int duration; // minutes
  private int caloriesBurned;

  @ManyToOne
  private Trainer trainer;

  @ManyToOne
  @JoinColumn(name = "member_id")
  @JsonBackReference
  private Member member;

  private double sessionCost;

  public WorkoutSession(int sessionId, LocalDate date, int duration,
      int caloriesBurned, Trainer trainer, Member member) {
    this.sessionId = sessionId;
    this.date = date;
    this.duration = duration;
    this.caloriesBurned = caloriesBurned;
    this.trainer = trainer;
    this.member = member;
    if (trainer != null) {
      this.sessionCost = trainer.calculateSessionCost();
    }
  }

  @PrePersist
  public void prePersist() {
    if (trainer != null) {
      this.sessionCost = trainer.calculateSessionCost();
    }
  }

  public double getSessionCost() {
    return sessionCost;
  }

  public int getSessionId() {
    return sessionId;
  }

  public void setSessionId(int sessionId) {
    this.sessionId = sessionId;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public int getCaloriesBurned() {
    return caloriesBurned;
  }

  public void setCaloriesBurned(int caloriesBurned) {
    this.caloriesBurned = caloriesBurned;
  }

  public Trainer getTrainer() {
    return trainer;
  }

  public void setTrainer(Trainer trainer) {
    this.trainer = trainer;
  }

  public Member getMember() {
    return member;
  }

  public void setMember(Member member) {
    this.member = member;
  }

  public void displaySessionInfo() {
    System.out.println("Session ID: " + sessionId);
    System.out.println("Date: " + date);
    System.out.println("Duration: " + duration + " minutes");
    System.out.println("Calories Burned: " + caloriesBurned);

    if (trainer != null)
      System.out.println("Trainer: " + trainer.getName());

    if (member != null)
      System.out.println("Member: " + member.getName());

    System.out.println("-------------------------------------");
  }
}
