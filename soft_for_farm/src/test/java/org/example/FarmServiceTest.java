package org.example;

import org.example.entity.Farm;
import org.example.entity.User;
import org.example.repository.FarmRepository;
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
public class FarmServiceTest {

    @Autowired
    FarmRepository farmRepository;
    @Autowired
    FarmService farmService;
    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        assertThat(farmRepository).isNotNull();
        assertThat(farmService).isNotNull();
        assertThat(userService).isNotNull();
    }

    @Test
    void createFarm() throws Exception {
        User user = userService.findByLogin("test user");
        Farm farm = new Farm("test farm", "2021", "test address");
        farmService.addFarm(user, farm);
        Long id = farm.getId();

        Assertions.assertTrue(farmRepository.existsById(id));
        farmService.deleteFarm(user, 0L);
    }

    @Test
    void updateFarm() throws Exception {
        User user = userService.findByLogin("test user");
        Farm farm = new Farm("test farm", "2021", "test address");
        farmService.addFarm(user, farm);
        Farm newFarm = new Farm("test farm updated", "2021", "test address");
        Long id = farm.getId();

        farmService.updateFarm(user, id, newFarm);
        Assertions.assertEquals(newFarm.getFarmName(), farmService.findFarmById(user, id).getFarmName());
        farmService.deleteFarm(user, 0L);
    }

    @Test
    void deleteFarm() throws Exception {
        User user = userService.findByLogin("test user");
        Farm farm = new Farm("test farm", "2021", "test address");
        farmService.addFarm(user, farm);
        Long id = farm.getId();

        farmService.deleteFarm(user, id);
        Assertions.assertFalse(farmRepository.existsById(id));
    }
}
