package org.example;

import org.example.entity.Farm;
import org.example.repository.FarmRepository;
import org.example.service.FarmService;
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
public class FarmServiceTest {

    @Autowired
    FarmRepository farmRepository;
    @Autowired
    FarmService farmService;


    @Test
    void contextLoads() {
        assertThat(farmRepository).isNotNull();
        assertThat(farmService).isNotNull();
    }

    @Test
    @WithMockUser(username = "test user", password = "adminpass")
    void createFarm() throws Exception {
        Farm farm = new Farm("test farm", "2021", "test address");
        farmService.addFarm(farm);
        Long id = farm.getId();

        Assertions.assertTrue(farmRepository.existsById(id));
        farmService.deleteFarm(id);
    }

    @Test
    @WithMockUser(username = "test user", password = "adminpass")
    void updateFarm() throws Exception {
        Farm farm = new Farm("test farm", "2021", "test address");
        farmService.addFarm(farm);
        Farm newFarm = new Farm("test farm updated", "2021", "test address");
        Long id = farm.getId();

        farmService.updateFarm(id, newFarm);
        Assertions.assertEquals(newFarm.getFarmName(), farmService.findFarmById(id).getFarmName());
        farmService.deleteFarm(id);
    }

    @Test
    @WithMockUser(username = "test user", password = "adminpass")
    void deleteFarm() throws Exception {
        Farm farm = new Farm("test farm", "2021", "test address");
        farmService.addFarm(farm);
        Long id = farm.getId();

        farmService.deleteFarm(id);
        Assertions.assertFalse(farmRepository.existsById(id));
    }
}
