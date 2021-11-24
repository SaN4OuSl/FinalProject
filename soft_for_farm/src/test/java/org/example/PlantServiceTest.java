package org.example;

import org.example.entity.Farm;
import org.example.entity.Plant;
import org.example.repository.PlantRepository;
import org.example.service.FarmService;
import org.example.service.PlantService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"create-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"delete-database.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PlantServiceTest {
    
    @Autowired
    PlantRepository plantRepository;
    
    @Autowired
    PlantService plantService;
    
    @Autowired
    FarmService farmService;

    
    @Test
    void contextLoads() {
        assertThat(plantRepository).isNotNull();
        assertThat(plantService).isNotNull();
        assertThat(farmService).isNotNull();
    }
    
    @Test
    @WithMockUser(username = "test user", password = "adminpass")
    void createPlant() throws Exception {
        Farm farm = new Farm("test farm", "2021", "test address");
        farmService.addFarm(farm);
        Long farmId = farm.getId();
        Plant plant = new Plant("test plant", 2000.2, 200000d, 2111.11, 2000.00, 55.00, 600.00);
        plantService.addPlant(farmId, plant);
        Long plantId = plant.getId();
        
        Assertions.assertTrue(plantRepository.existsById(plantId));
        plantService.deletePlant(plantId);
        farmService.deleteFarm(farmId);
    }
    
    @Test
    @WithMockUser(username = "test user", password = "adminpass")
    void updatePlant() throws Exception {
        Farm farm = new Farm("test farm", "2021", "test address");
        farmService.addFarm(farm);
        Long farmId = farm.getId();
        
        Plant plant = new Plant("test plant", 2000.2, 200000d, 2111.11, 2000.00, 55.00, 600.00);
        plantService.addPlant(farmId, plant);
        Long plantId = plant.getId();
        
        Plant newPlant = new Plant("test plant updated", 2000.2, 200000d, 2111.11, 2000.00, 55.00, 600.00);
        plantService.updatePlant(plantId, newPlant);
        
        Assertions.assertEquals(newPlant.getPlantName(), plantService.findPlantById(plantId).getPlantName());
        plantService.deletePlant(plantId);
        farmService.deleteFarm(farmId);
    }
    
    @Test
    @WithMockUser(username = "test user", password = "adminpass")
    void deletePlant() throws Exception {
        Farm farm = new Farm("test farm", "2021", "test address");
        farmService.addFarm(farm);
        Long farmId = farm.getId();
        Plant plant = new Plant("test plant", 2000.2, 200000d, 2111.11, 2000.00, 55.00, 600.00);
        plantService.addPlant(farmId, plant);
        Long plantId = plant.getId();
        
        plantService.deletePlant(plantId);
        Assertions.assertFalse(plantRepository.existsById(plantId));
        farmService.deleteFarm(farmId);
    }
}