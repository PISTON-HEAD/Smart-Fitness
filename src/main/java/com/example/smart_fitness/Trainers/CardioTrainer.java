package com.example.smart_fitness.Trainers;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Cardio")
public class CardioTrainer extends Trainer {

 public CardioTrainer(int id, String name, int experienceYears) {
  super(id, name, experienceYears, "Cardio");
 }

 public CardioTrainer(String name, int experienceYears) {
  super(name, experienceYears, "Cardio");
 }

 public CardioTrainer() {
 }

 @Override
 public double calculateSessionCost() {
  return 600 + (experienceYears * 30);
 }
}
