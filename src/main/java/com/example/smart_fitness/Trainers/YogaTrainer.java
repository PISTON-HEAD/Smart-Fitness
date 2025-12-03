package com.example.smart_fitness.Trainers;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Yoga")
public class YogaTrainer extends Trainer {

 public YogaTrainer() {
  super();
 }

 public YogaTrainer(String name, int experienceYears) {
  super(name, experienceYears, "Yoga");
 }

 public YogaTrainer(int id, String name, int experienceYears) {
  super(id, name, experienceYears, "Yoga");
 }

 @Override
 public double calculateSessionCost() {
  return 500 + (experienceYears * 20);
 }
}
