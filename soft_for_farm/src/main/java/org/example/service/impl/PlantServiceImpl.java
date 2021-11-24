package org.example.service.impl;

import org.example.entity.Plant;
import org.example.entity.User;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.exception.plant.AccessToPlantException;
import org.example.exception.plant.PlantNotFoundException;
import org.example.exception.user.UserNotFoundException;
import org.example.repository.FarmRepository;
import org.example.repository.PlantRepository;
import org.example.service.PlantService;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantServiceImpl implements PlantService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PlantServiceImpl.class);
    
    private final PlantRepository plantRepository;
    private final FarmRepository farmRepository;
    private final UserService userService;
    
    public PlantServiceImpl(PlantRepository plantRepository, FarmRepository farmRepository, UserService userService) {
        this.plantRepository = plantRepository;
        this.farmRepository = farmRepository;
        this.userService = userService;
    }
    
    @Override
    public Plant addPlant(Long farmId, Plant plant) throws AccessToFarmException, UserNotFoundException, FarmNotFoundException {
        if (!farmRepository.existsById(farmId)) {
            throw new FarmNotFoundException("Farm with this id not found");
        }
        User user = userService.getUserByAuthentication();
        if (!farmRepository.existsByIdAndUser(farmId, user)) {
            throw new AccessToFarmException("You don't have access to this farm");
        }
        LOGGER.info("Start create plant: " + plant.getId());
        plant.setFarm(farmRepository.findById(farmId).orElse(null));
        plantRepository.save(plant);
        LOGGER.info("End create field: " + plant.getId());
        return plant;
    }
    
    @Override
    public Plant updatePlant(Long id, Plant plant) throws UserNotFoundException, PlantNotFoundException {
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
        return newPlant;
    }
    
    @Override
    public void deletePlant(Long id) throws UserNotFoundException, PlantNotFoundException, AccessToPlantException {
        if (plantRepository.existsById(id)) {
            User user = userService.getUserByAuthentication();
            if (checkAccessToPlant(user, id)) {
                LOGGER.info("Start delete plant: " + id);
                plantRepository.deleteById(id);
                LOGGER.info("End delete plant: " + id);
            } else {
                LOGGER.warn("User dont have access to this plant");
                throw new AccessToPlantException("You dont have access to this plant");
            }
        } else {
            LOGGER.warn("Plant doesn't exists");
            throw new PlantNotFoundException("Plant doesn't exists");
        }
    }
    
    @Override
    public Plant findPlantById(Long id) throws UserNotFoundException, PlantNotFoundException {
        User user = userService.getUserByAuthentication();
        Plant plant = plantRepository.findByIdAndUser(id, user);
        if (plant != null)
            return plant;
        else throw new PlantNotFoundException("Plant with this user and id not found");
    }
    
    @Override
    public Double profitCounter(Long id) {
        return plantRepository.countProfitOfPlantById(id);
    }
    
    @Override
    public Double expensesCounter(Long id) {
        return plantRepository.countExpensesOfPlantById(id);
    }
    
    @Override
    public Double netProfitCounter(Long id) {
        return profitCounter(id) - expensesCounter(id);
    }
    
    @Override
    public List<Plant> findAllPlantsByFarmId(Long farmId) throws FarmNotFoundException, AccessToFarmException, UserNotFoundException {
        LOGGER.info("Read all plants by farm");
        if (!farmRepository.existsById(farmId)) {
            LOGGER.info("Farm with this id not found");
            throw new FarmNotFoundException("Farm with this id not found");
        }
        User user = userService.getUserByAuthentication();
        if (!farmRepository.existsByIdAndUser(farmId, user)) {
            LOGGER.info("User doesn't have access to this farm");
            throw new AccessToFarmException("You don't have access to this farm");
        }
        return plantRepository.findAllByFarmId(farmId);
    }
    
    private boolean checkAccessToPlant(User user, Long id) {
        return plantRepository.existsByIdAndUser(id, user);
    }
}
