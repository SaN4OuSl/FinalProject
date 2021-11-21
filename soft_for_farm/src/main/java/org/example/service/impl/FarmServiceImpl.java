package org.example.service.impl;

import org.example.entity.*;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.repository.FarmRepository;
import org.example.service.AnimalService;
import org.example.service.FarmService;
import org.example.service.PlantService;
import org.example.service.TechniqueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmServiceImpl implements FarmService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FarmServiceImpl.class);
    
    private final FarmRepository farmRepository;
    private final PlantService plantService;
    private final AnimalService animalService;
    private final TechniqueService techniqueService;
    
    @Autowired
    public FarmServiceImpl(FarmRepository farmRepository, PlantService plantService, AnimalService animalService, TechniqueService techniqueService) {
        this.farmRepository = farmRepository;
        this.plantService = plantService;
        this.animalService = animalService;
        this.techniqueService = techniqueService;
    }
    
    @Override
    public void addFarm(User user, Farm farm) {
        if (farm != null) {
            LOGGER.info("Start create farm: " + farm.getFarmName());
            farm.setUser(user);
            farmRepository.save(farm);
            LOGGER.info("End create farm: " + farm.getFarmName());
        } else {
            LOGGER.warn("Farm is null!");
        }
    }
    
    @Override
    public void updateFarm(User user, Long id, Farm farm) throws FarmNotFoundException, AccessToFarmException {
        if (farmRepository.existsById(id)) {
            if (farmRepository.existsByIdAndUser(id, user)) {
                LOGGER.info("Start update farm: " + id);
                Farm newFarm = findFarmById(user, id);
                newFarm.setFarmName(farm.getFarmName());
                newFarm.setAddress(farm.getAddress());
                newFarm.setYearOfStatistic(farm.getYearOfStatistic());
                farmRepository.save(newFarm);
                LOGGER.info("End update farm: " + id);
            } else {
                LOGGER.warn("You don't have access to this farm");
                throw new AccessToFarmException("You don't have access to this farm");
            }
        } else {
            LOGGER.warn("farm doesn't exists");
            throw new FarmNotFoundException("Farm with this id not found");
        }
    }
    
    @Override
    public Farm findFarmById(User user, Long id) throws FarmNotFoundException, AccessToFarmException {
        if (!farmRepository.existsById(id)) {
            throw new FarmNotFoundException("Farm with this id not found");
        }
        if (!farmRepository.existsByIdAndUser(id, user)) {
            throw new AccessToFarmException("You don't have access to this farm");
        }
        return farmRepository.findById(id).orElse(null);
    }
    
    @Override
    public void deleteFarm(User user, Long id) throws AccessToFarmException, FarmNotFoundException {
        if (farmRepository.existsById(id)) {
            if (farmRepository.existsByIdAndUser(id, user)) {
                LOGGER.info("Start delete farm: " + id);
                farmRepository.deleteById(id);
                LOGGER.info("End delete farm: " + id);
            } else {
                LOGGER.warn("You don't have access to this farm");
                throw new AccessToFarmException("You don't have access to this farm");
            }
        } else {
            LOGGER.warn("farm doesn't exists");
            throw new FarmNotFoundException("Farm with this id not found");
        }
    }
    
    @Override
    public Double profitCounter(Farm farm) {
        List<Plant> plants = farm.getPlants();
        List<Animal> animals = farm.getAnimals();
        Double profit = 0d;
        for (Plant plant : plants) {
            profit += plantService.profitCounter(plant.getId());
        }
        for (Animal animal : animals) {
            profit += animalService.profitCounter(animal.getId());
        }
        return profit;
    }
    
    @Override
    public Double expensesCounter(Farm farm) {
        List<Plant> plants = farm.getPlants();
        List<Animal> animals = farm.getAnimals();
        List<Technique> techniques = farm.getTechniques();
        Double expense = 0d;
        for (Plant plant : plants) {
            expense += plantService.expensesCounter(plant.getId());
        }
        for (Animal animal : animals) {
            expense += animalService.expensesCounter(animal.getId());
        }
        for (Technique technique : techniques) {
            expense += techniqueService.expensesCounter(technique.getId());
        }
        return expense;
    }
    
    @Override
    public Double netProfitCounter(Farm farm) {
        return profitCounter(farm) - expensesCounter(farm);
    }
    
    @Override
    public Page<Farm> findFarmsByYear(String year, User user, Pageable pageable) {
        return farmRepository.findAllByYearOfStatisticAndUser(year, user, pageable);
    }
    
    @Override
    public Page<Farm> findFarmsByFarmName(String farmName, User user, Pageable pageable) {
        return farmRepository.findAllByFarmNameAndUser(farmName, user, pageable);
    }
    
    @Override
    public Page<Farm> findAllPageable(User user, Pageable pageable) {
        LOGGER.info("Read all farms");
        return farmRepository.findAllByUser(user, pageable);
    }
}
