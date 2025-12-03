package com.example.smart_fitness.Trainers;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
@DiscriminatorColumn(name = "Type")
public abstract class Trainer {
 @Id
 @GeneratedValue
 protected int id;
 protected String name;
 protected int experienceYears;
 protected String specialization;

 public Trainer() {
 }

 public Trainer(int id, String name, int experienceYears, String specialization) {
  this.id = id;
  this.name = name;
  this.experienceYears = experienceYears;
  this.specialization = specialization;
 }

 public Trainer(String name, int experienceYears, String specialization) {
  this.name = name;
  this.experienceYears = experienceYears;
  this.specialization = specialization;
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

 public int getExperienceYears() {
  return experienceYears;
 }

 public void setExperienceYears(int experienceYears) {
  this.experienceYears = experienceYears;
 }

 public String getSpecialization() {
  return specialization;
 }

 public void setSpecialization(String specialization) {
  this.specialization = specialization;
 }

 public abstract double calculateSessionCost();

 public void displayInfo() {
  System.out.println("ID: " + id);
  System.out.println("Name: " + name);
  System.out.println("Experience: " + experienceYears + " years");
  System.out.println("Specialization: " + specialization);
  System.out.println("Session Cost: " + calculateSessionCost());
  System.out.println("-------------------------------------");
 }
}
