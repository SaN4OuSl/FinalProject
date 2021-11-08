package org.example.service.impl;

import org.example.entity.Animal;
import org.example.entity.Farm;
import org.example.repository.animal.AnimalRepository;
import org.example.service.AnimalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AnimalServiceImpl.class);
    
    private final AnimalRepository animalRepository;
    
    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }
    
    @Override
    public void addAnimal(Farm farm, Animal animal) {
        if (animal != null) {
            LOGGER.info("Start create animal: " + animal.getId());
            animal.setFarm(farm);
            animalRepository.save(animal);
            LOGGER.info("End create animal: " + animal.getId());
        } else {
            LOGGER.warn("animal is null!");
        }
    }
    
    @Override
    public void updateAnimal(Long id, Animal animal) {
        if (animalRepository.existsById(id)) {
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
        } else {
            LOGGER.warn("animal doesn't exists");
        }
    }
    
    @Override
    public void deleteAnimal(Long id) {
        if (animalRepository.existsById(id)) {
            LOGGER.info("Start delete animal: " + id);
            animalRepository.deleteById(id);
            LOGGER.info("End delete animal: " + id);
        } else {
            LOGGER.warn("animal doesn't exists");
        }
    }
    
    @Override
    public Animal findAnimalById(Long id) {
        return animalRepository.findById(id).orElse(null);
    }
    
    @Override
    public Double profitCounter(Animal animal) {
        return animal.getCostOfOneAnimal() * animal.getNumberOfAnimals();
    }
    
    @Override
    public Double expensesCounter(Animal animal) {
        return animal.getOtherExpenses() + animal.getRentalPriceOfBuilding() + animal.getCostOfFeeds();
    }
    
    @Override
    public Double netProfitCounter(Animal animal) {
        return profitCounter(animal) - expensesCounter(animal);
    }
    
    @Override
    public List<Animal> findAllAnimal() {
        LOGGER.info("Read all animals");
        return animalRepository.findAll();
    }
}
