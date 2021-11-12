package org.example;

import org.example.entity.Animal;
import org.example.entity.Farm;
import org.example.entity.User;
import org.example.repository.AnimalRepository;
import org.example.service.AnimalService;
import org.example.service.FarmService;
import org.example.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"create-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"delete-database.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AnimalServiceTest {
    
    @Autowired
    AnimalRepository animalRepository;
    
    @Autowired
    AnimalService animalService;
    
    @Autowired
    FarmService farmService;
    
    @Autowired
    UserService userService;
    
    @Test
    void contextLoads() {
        assertThat(animalRepository).isNotNull();
        assertThat(animalService).isNotNull();
        assertThat(farmService).isNotNull();
        assertThat(userService).isNotNull();
    }
    
    @Test
    void createAnimal() throws Exception {
        User user = userService.findByLogin("test user");
        Farm farm = new Farm("test farm", "2021", "test address");
        farmService.addFarm(user, farm);
        Long farmId = farm.getId();
        Animal animal = new Animal("test animal", 2000, 200000d, 2111.11, 2000.00, 2222d);
        animalService.addAnimal(farmService.findFarmById(user, farmId), animal);
        Long animalId = animal.getId();
        
        Assertions.assertTrue(animalRepository.existsById(animalId));
        animalService.deleteAnimal(animalId);
        farmService.deleteFarm(user, farmId);
    }
    
    @Test
    void updateAnimal() throws Exception {
        User user = userService.findByLogin("test user");
        Farm farm = new Farm("test farm", "2021", "test address");
        farmService.addFarm(user, farm);
        Long farmId = farm.getId();
        
        Animal animal = new Animal("test animal", 2000, 200000d, 2111.11, 2000.00, 2222d);
        animalService.addAnimal(farmService.findFarmById(user, farmId), animal);
        Long animalId = animal.getId();
        
        Animal newAnimal = new Animal("test animal updated", 2000, 200000d, 2111.11, 2000.00, 2000.);
        animalService.updateAnimal(animalId, newAnimal);
        
        Assertions.assertEquals(newAnimal.getAnimalName(), animalService.findAnimalById(animalId).getAnimalName());
        animalService.deleteAnimal(animalId);
        farmService.deleteFarm(user, farmId);
    }
    
    @Test
    void deleteAnimal() throws Exception {
        User user = userService.findByLogin("test user");
        Farm farm = new Farm("test farm", "2021", "test address");
        farmService.addFarm(user, farm);
        Long farmId = farm.getId();
        Animal animal = new Animal("test animal", 2000, 200000d, 2111.11, 2000.00, 2222d);
        animalService.addAnimal(farmService.findFarmById(user, farmId), animal);
        Long animalId = animal.getId();
        
        animalService.deleteAnimal(animalId);
        Assertions.assertFalse(animalRepository.existsById(animalId));
        farmService.deleteFarm(user, farmId);
    }
}