package org.example.service;

import org.example.entity.Animal;
import org.example.entity.Farm;
import org.example.entity.User;

import java.util.List;

public interface AnimalService {
    
    void addAnimal(Farm farm, Animal animal);
    
    void updateAnimal(Long id, Animal animal);
    
    void deleteAnimal(Long id);
    
    Animal findAnimalById(Long id);
    
    Double profitCounter(Long id);
    
    Double expensesCounter(Long id);
    
    Double netProfitCounter(Long id);
    
    List<Animal> findAllAnimalByFarm(Farm farm);
    
    boolean checkAccessToAnimal(User user, Long id);
}
