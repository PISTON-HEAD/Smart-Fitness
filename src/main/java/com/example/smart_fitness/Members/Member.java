package com.example.smart_fitness.Members;

import java.util.ArrayList;
import java.util.List;

import com.example.smart_fitness.WorkoutSession.WorkoutSession;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Member {

 public Member() {
 }

 @Id
 @GeneratedValue
 private int id;
 private String name;
 private int age;
 private MembershipPlan membershipPlan;

 @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
 @JsonManagedReference
 private List<WorkoutSession> sessions;

 public Member(int id, String name, int age, MembershipPlan membershipPlan) {
  this.id = id;
  this.name = name;
  this.age = age;
  this.membershipPlan = membershipPlan;
  this.sessions = new ArrayList<>();
 }

 public Member(String name, int age, MembershipPlan membershipPlan) {
  this.name = name;
  this.age = age;
  this.membershipPlan = membershipPlan;
  this.sessions = new ArrayList<>();
 }

 public int getId() {
  return id;
 }

 public void setId(int id) {
  this.id = id;
 }

 public String getName() {
  return name;
 }

 public void setName(String name) {
  this.name = name;
 }

 public int getAge() {
  return age;
 }

 public void setAge(int age) {
  this.age = age;
 }

 public MembershipPlan getMembershipPlan() {
  return membershipPlan;
 }

 public void setMembershipPlan(MembershipPlan membershipPlan) {
  this.membershipPlan = membershipPlan;
 }

 public List<WorkoutSession> getSessions() {
  return sessions;
 }

 public void addSession(WorkoutSession session) {
  sessions.add(session);
 }

 public void displayMemberInfo() {
  System.out.println("Member ID: " + id);
  System.out.println("Name: " + name);
  System.out.println("Age: " + age);
  System.out.println("Plan: " + membershipPlan);
  System.out.println("Total Sessions: " + sessions.size());
 }
}
