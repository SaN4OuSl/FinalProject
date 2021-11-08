package org.example.service.impl;

import org.example.entity.Farm;
import org.example.entity.Plant;
import org.example.repository.plant.PlantRepository;
import org.example.service.PlantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantServiceImpl implements PlantService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PlantServiceImpl.class);
    
    private final PlantRepository plantRepository;
    
    public PlantServiceImpl(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }
    
    @Override
    public void addPlant(Farm farm, Plant plant) {
        if (plant != null) {
            LOGGER.info("Start create plant: " + plant.getId());
            plant.setFarm(farm);
            plantRepository.save(plant);
            LOGGER.info("End create field: " + plant.getId());
        } else {
            LOGGER.warn("plant is null!");
        }
    }
    
    @Override
    public void updatePlant(Long id, Plant plant) {
        if (plantRepository.existsById(id)) {
            LOGGER.info("Start update plant: " + id);
            Plant newPlant = findPlantById(id);
            newPlant.setCostOfPlant(plant.getCostOfPlant());
            newPlant.setSizeOfFieldForPlant(plant.getSizeOfFieldForPlant());
            newPlant.setPlantHarvest(plant.getPlantHarvest());
            newPlant.setCostOfFertilizers(plant.getCostOfFertilizers());
            newPlant.setOtherExpense(plant.getOtherExpense());
            newPlant.setRentalPriceOfField(plant.getRentalPriceOfField());
            newPlant.setPlantName(plant.getPlantName());
            plantRepository.save(newPlant);
            LOGGER.info("End update plant: " + id);
        } else {
            LOGGER.warn("plant doesn't exists");
        }
    }
    
    @Override
    public void deletePlant(Long id) {
        if (plantRepository.existsById(id)) {
            LOGGER.info("Start delete plant: " + id);
            plantRepository.deleteById(id);
            LOGGER.info("End delete plant: " + id);
        } else {
            LOGGER.warn("plant doesn't exists");
        }
    }
    
    @Override
    public Plant findPlantById(Long id) {
        return plantRepository.findById(id).orElse(null);
    }
    
    @Override
    public Double profitCounter(Plant plant) {
        return plant.getCostOfPlant() * plant.getPlantHarvest() * plant.getSizeOfFieldForPlant();
    }
    
    @Override
    public Double expensesCounter(Plant plant) {
        return plant.getCostOfFertilizers() + plant.getRentalPriceOfField() + plant.getOtherExpense();
    }
    
    @Override
    public Double netProfitCounter(Plant plant) {
        return profitCounter(plant) - expensesCounter(plant);
    }
    
    @Override
    public List<Plant> findAllPlants() {
        LOGGER.info("Read all plants");
        return plantRepository.findAll();
    }
    
}
