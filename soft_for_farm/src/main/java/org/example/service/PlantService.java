package org.example.service;

import org.example.model.Farm;
import org.example.model.Plant;

import java.util.List;

public interface PlantService {
    
    void addPlant(Farm farm, Plant plant);
    
    void updatePlant(Long id, Plant plant);
    
    void deletePlant(Long id);
    
    Plant findPlantById(Long id);
    
    Double profitCounter(Plant plant);
    
    Double expensesCounter(Plant plant);
    
    Double netProfitCounter(Plant plant);
    
    List<Plant> findAllPlants();
}
