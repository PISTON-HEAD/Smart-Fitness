package com.example.smart_fitness.Trainers;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Strength Training")
public class StrengthTrainer extends Trainer {

 public StrengthTrainer(int id, String name, int experienceYears) {
  super(id, name, experienceYears, "Strength Training");
 }

 public StrengthTrainer(String name, int experienceYears) {
  super(name, experienceYears, "Strength Training");
 }

 public StrengthTrainer() {
 }

 @Override
 public double calculateSessionCost() {
  return 800 + (experienceYears * 50);
 }
}
