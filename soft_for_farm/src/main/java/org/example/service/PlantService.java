package org.example.service;

import org.example.entity.Farm;
import org.example.entity.Plant;
import org.example.entity.User;

import java.util.List;

public interface PlantService {
    
    void addPlant(Farm farm, Plant plant);
    
    void updatePlant(Long id, Plant plant);
    
    void deletePlant(Long id);
    
    Plant findPlantById(Long id);
    
    Double profitCounter(Plant plant);
    
    Double expensesCounter(Plant plant);
    
    Double netProfitCounter(Plant plant);
    
    List<Plant> findAllPlantsByFarm(Farm farm);
    
    boolean checkAccessToPlant(User user, Long id);
}
