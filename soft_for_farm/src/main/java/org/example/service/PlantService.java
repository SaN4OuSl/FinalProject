package org.example.service;

import org.example.entity.Plant;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.exception.plant.AccessToPlantException;
import org.example.exception.plant.PlantNotFoundException;
import org.example.exception.user.UserNotFoundException;

import java.util.List;

public interface PlantService {
    
    Plant addPlant(Long farmId, Plant plant) throws AccessToFarmException, UserNotFoundException, FarmNotFoundException;
    
    Plant updatePlant(Long id, Plant plant) throws UserNotFoundException, PlantNotFoundException;
    
    void deletePlant(Long id) throws UserNotFoundException, PlantNotFoundException, AccessToPlantException;
    
    Plant findPlantById(Long id) throws UserNotFoundException, PlantNotFoundException;
    
    Double profitCounter(Long id);
    
    Double expensesCounter(Long id);
    
    Double netProfitCounter(Long id);
    
    List<Plant> findAllPlantsByFarmId(Long farmId) throws FarmNotFoundException, AccessToFarmException, UserNotFoundException;
}
