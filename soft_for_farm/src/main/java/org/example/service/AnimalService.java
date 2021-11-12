package org.example.service;

import org.example.entity.Animal;
import org.example.entity.Farm;

import java.util.List;

public interface AnimalService {
    
    void addAnimal(Farm farm, Animal animal);
    
    void updateAnimal(Long id, Animal animal);
    
    void deleteAnimal(Long id);
    
    Animal findAnimalById(Long id);
    
    Double profitCounter(Animal animal);
    
    Double expensesCounter(Animal animal);
    
    Double netProfitCounter(Animal animal);
    
    List<Animal> findAllAnimalByFarm(Farm farm);
}
