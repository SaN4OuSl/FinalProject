package org.example.service.impl;

import org.example.entity.Animal;
import org.example.entity.Farm;
import org.example.entity.User;
import org.example.exception.animal.AccessToAnimalException;
import org.example.exception.animal.AnimalNotFoundException;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.exception.user.UserNotFoundException;
import org.example.repository.AnimalRepository;
import org.example.repository.FarmRepository;
import org.example.service.AnimalService;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AnimalServiceImpl.class);
    
    private final AnimalRepository animalRepository;
    private final FarmRepository farmRepository;
    private final UserService userService;
    
    public AnimalServiceImpl(AnimalRepository animalRepository, FarmRepository farmRepository, UserService userService) {
        this.animalRepository = animalRepository;
        this.farmRepository = farmRepository;
        this.userService = userService;
    }
    
    @Override
    public Animal addAnimal(Long farmId, Animal animal) throws FarmNotFoundException, AccessToFarmException, UserNotFoundException {
        if (!farmRepository.existsById(farmId)) {
            throw new FarmNotFoundException("Farm with this id not found");
        }
        User user = userService.getUserByAuthentication();
        if (!farmRepository.existsByIdAndUser(farmId, user)) {
            throw new AccessToFarmException("You don't have access to this farm");
        }
        LOGGER.info("Start create animal: " + animal.getId());
        animal.setFarm(farmRepository.findById(farmId).orElse(null));
        animalRepository.save(animal);
        LOGGER.info("End create animal: " + animal.getId());
        return animal;
    }
    
    @Override
    public Animal updateAnimal(Long id, Animal animal) throws UserNotFoundException, AnimalNotFoundException {
        LOGGER.info("Start update animal: " + id);
        Animal newAnimal = findAnimalById(id);
        newAnimal.setAnimalName(animal.getAnimalName());
        newAnimal.setCostOfOneAnimal(animal.getCostOfOneAnimal());
        newAnimal.setNumberOfAnimals(animal.getNumberOfAnimals());
        newAnimal.setCostOfFeeds(animal.getCostOfFeeds());
        newAnimal.setRentalPriceOfBuilding(animal.getRentalPriceOfBuilding());
        newAnimal.setOtherExpenses(animal.getOtherExpenses());
        animalRepository.save(newAnimal);
        LOGGER.info("End update animal: " + id);
        return newAnimal;
    }
    
    @Override
    public void deleteAnimal(Long id) throws UserNotFoundException, AccessToAnimalException, AnimalNotFoundException {
        if (animalRepository.existsById(id)) {
            User user = userService.getUserByAuthentication();
            if (checkAccessToAnimal(user, id)) {
                LOGGER.info("Start delete animal: " + id);
                animalRepository.deleteById(id);
                LOGGER.info("End delete animal: " + id);
            } else {
                LOGGER.warn("User dont have access to this animal");
                throw new AccessToAnimalException("You dont have access to this animal");
            }
        } else {
            LOGGER.warn("Animal doesn't exists");
            throw new AnimalNotFoundException("Animal doesn't exists");
        }
    }
    
    @Override
    public Animal findAnimalById(Long id) throws UserNotFoundException, AnimalNotFoundException {
        User user = userService.getUserByAuthentication();
        Animal animal = animalRepository.findByIdAndUser(id, user);
        if (animal != null)
            return animal;
        else throw new AnimalNotFoundException("Animal with this user and id not found");
    }
    
    @Override
    public Double profitCounter(Long id) {
        return animalRepository.countProfitOfAnimalById(id);
    }
    
    @Override
    public Double expensesCounter(Long id) {
        return animalRepository.countExpensesOfAnimalById(id);
    }
    
    @Override
    public Double netProfitCounter(Long id) {
        return profitCounter(id) - expensesCounter(id);
    }
    
    @Override
    public List<Animal> findAllAnimalByFarmId(Long farmId) throws AccessToFarmException, FarmNotFoundException, UserNotFoundException {
        LOGGER.info("Read all animals by farm");
        if (!farmRepository.existsById(farmId)) {
            LOGGER.info("Farm with this id not found");
            throw new FarmNotFoundException("Farm with this id not found");
        }
        User user = userService.getUserByAuthentication();
        if (!farmRepository.existsByIdAndUser(farmId, user)) {
            LOGGER.info("User doesn't have access to this farm");
            throw new AccessToFarmException("You don't have access to this farm");
        }
        return animalRepository.findAllByFarmId(farmId);
    }
    
    @Override
    public Farm getFarmByAnimalId(Long id) {
        return farmRepository.getFarmByAnimalId(id);
    }
    
    private boolean checkAccessToAnimal(User user, Long id) {
        return animalRepository.existsByIdAndUser(id, user);
    }
}
