package org.example;

import org.example.entity.*;
import org.example.repository.TechniqueRepository;
import org.example.service.FarmService;
import org.example.service.TechniqueService;
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
public class TechniqueServiceTest {
    
    @Autowired
    TechniqueRepository techniqueRepository;
    
    @Autowired
    TechniqueService techniqueService;
    
    @Autowired
    FarmService farmService;
    
    @Test
    void contextLoads() {
        assertThat(techniqueRepository).isNotNull();
        assertThat(techniqueService).isNotNull();
        assertThat(farmService).isNotNull();
    }
    
    @Test
    @WithMockUser(username = "test user", password = "adminpass")
    void createTechnique() throws Exception {
        Farm farm = new Farm("test farm", "2021", "test address");
        farmService.addFarm(farm);
        Long farmId = farm.getId();
        Technique technique = new Technique("test technique", 1000.1, 20000.1);
        techniqueService.addTechnique(farmId, technique);
        Long techniqueId = technique.getId();
        
        Assertions.assertTrue(techniqueRepository.existsById(techniqueId));
        techniqueService.deleteTechnique(techniqueId);
        farmService.deleteFarm(farmId);
    }
    
    @Test
    @WithMockUser(username = "test user", password = "adminpass")
    void updateTechnique() throws Exception {
        Farm farm = new Farm("test farm", "2021", "test address");
        farmService.addFarm(farm);
        Long farmId = farm.getId();
    
        Technique technique = new Technique("test technique", 1000.1, 20000.1);
        techniqueService.addTechnique(farmId, technique);
        Long techniqueId = technique.getId();
    
        Technique newTechnique =new Technique("test technique updated", 1000.1, 20000.1);
        techniqueService.updateTechnique(techniqueId, newTechnique);

        Assertions.assertEquals(newTechnique.getTypeOfTechnique(), techniqueService.findTechniqueById(techniqueId).getTypeOfTechnique());
        techniqueService.deleteTechnique(techniqueId);
        farmService.deleteFarm(farmId);
    }

    @Test
    @WithMockUser(username = "test user", password = "adminpass")
    void deleteTechnique() throws Exception {
        Farm farm = new Farm("test farm", "2021", "test address");
        farmService.addFarm(farm);
        Long farmId = farm.getId();
        Technique technique = new Technique("test technique", 1000.1, 20000.1);
        techniqueService.addTechnique(farmId, technique);
        Long techniqueId = technique.getId();

        techniqueService.deleteTechnique(techniqueId);
        Assertions.assertFalse(techniqueRepository.existsById(techniqueId));
        farmService.deleteFarm(farmId);
    }
}