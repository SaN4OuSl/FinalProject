package org.example.service.impl;

import org.example.model.Animal;
import org.example.model.Farm;
import org.example.model.Plant;
import org.example.model.Technique;
import org.example.model.User;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.repository.FarmRepository;
import org.example.repository.auth.UserRepository;
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

import java.security.Principal;
import java.util.List;

@Service
public class FarmServiceImpl implements FarmService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FarmServiceImpl.class);
    
    private final FarmRepository farmRepository;
    private final UserRepository userRepository;
    private final PlantService plantService;
    private final AnimalService animalService;
    private final TechniqueService techniqueService;
    
    @Autowired
    public FarmServiceImpl(FarmRepository farmRepository, UserRepository userRepository, PlantService plantService, AnimalService animalService, TechniqueService techniqueService) {
        this.farmRepository = farmRepository;
        this.userRepository = userRepository;
        this.plantService = plantService;
        this.animalService = animalService;
        this.techniqueService = techniqueService;
    }
    
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
    
    public void updateFarm(Principal principal, Long id, Farm farm) throws FarmNotFoundException, AccessToFarmException {
        if (farmRepository.existsById(id)) {
            LOGGER.info("Start update farm: " + id);
            Farm newFarm = findFarmById(principal, id);
            newFarm.setFarmName(farm.getFarmName());
            newFarm.setAddress(farm.getAddress());
            newFarm.setYearOfStatistic(farm.getYearOfStatistic());
            farmRepository.save(newFarm);
            LOGGER.info("End update farm: " + id);
        } else {
            LOGGER.warn("farm doesn't exists");
        }
    }
    
    public Farm findFarmById(Principal principal, Long id) throws FarmNotFoundException, AccessToFarmException {
        if (!farmRepository.existsById(id)) {
            throw new FarmNotFoundException("Farm with this id not found");
        }
        if (!userRepository.findByLogin(principal.getName()).getFarms().contains(farmRepository.findById(id).orElse(null))) {
            throw new AccessToFarmException("You don't have access to this farm");
        }
        return farmRepository.findById(id).orElse(null);
    }
    
    public void deleteFarm(User user, Long id) {
        if (farmRepository.existsById(id)) {
            LOGGER.info("Start delete farm: " + id);
            farmRepository.deleteById(id);
            LOGGER.info("End delete farm: " + id);
        } else {
            LOGGER.warn("farm doesn't exists");
        }
    }
    
    @Override
    public Double profitCounter(Farm farm) {
        List<Plant> plants = farm.getPlants();
        List<Animal> animals = farm.getAnimals();
        Double profit = 0d;
        for (Plant plant : plants) {
            profit += plantService.profitCounter(plant);
        }
        for (Animal animal : animals) {
            profit += animalService.profitCounter(animal);
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
            expense += plantService.expensesCounter(plant);
        }
        for (Animal animal : animals) {
            expense += animalService.expensesCounter(animal);
        }
        for (Technique technique : techniques) {
            expense += techniqueService.expensesCounter(technique);
        }
        return expense;
    }
    
    @Override
    public Double netProfitCounter(Farm farm) {
        return profitCounter(farm) - expensesCounter(farm);
    }
    
    @Override
    public List<Farm> findFarmsByYear(String year, User user) {
        return farmRepository.findAllByYearOfStatisticAndUser(year, user);
    }
    
    @Override
    public List<Farm> findFarmsByFarmName(String farmName, User user) {
        return farmRepository.findAllByFarmNameAndUser(farmName, user);
    }
    
    @Override
    public Page<Farm> findAllPageable(User user, Pageable pageable) {
        LOGGER.info("Read all farms");
        return farmRepository.findAllByUser(user, pageable);
    }
}
