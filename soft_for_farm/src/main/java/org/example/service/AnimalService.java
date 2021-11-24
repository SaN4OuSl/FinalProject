package org.example.service;

import org.example.entity.Animal;
import org.example.entity.Farm;
import org.example.exception.animal.AccessToAnimalException;
import org.example.exception.animal.AnimalNotFoundException;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.exception.user.UserNotFoundException;

import java.util.List;

public interface AnimalService {
    
    Animal addAnimal(Long farmId, Animal animal) throws UserNotFoundException, FarmNotFoundException, AccessToFarmException;
    
    Animal updateAnimal(Long id, Animal animal) throws UserNotFoundException, AnimalNotFoundException;
    
    void deleteAnimal(Long id) throws UserNotFoundException, AccessToAnimalException, AnimalNotFoundException;
    
    Animal findAnimalById(Long id) throws UserNotFoundException, AnimalNotFoundException;
    
    Double profitCounter(Long id);
    
    Double expensesCounter(Long id);
    
    Double netProfitCounter(Long id);
    
    List<Animal> findAllAnimalByFarmId(Long farmId) throws UserNotFoundException, FarmNotFoundException, AccessToFarmException;
    
    Farm getFarmByAnimalId(Long id);
}
