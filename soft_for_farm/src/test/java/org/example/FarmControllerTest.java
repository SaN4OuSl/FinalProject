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
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"create-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"delete-database.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class FarmControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
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
        this.mockMvc.perform(patch("/farm/" + id)
                        .with(user("test user"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("farmName", newFarm.getFarmName())
                        .param("yearOfStatistic", newFarm.getYearOfStatistic())
                        .param("address", newFarm.getAddress()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/farms"));
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
        this.mockMvc.perform(delete("/farm/" + id)
                        .with(user("test user"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/farms"));
        
        Assertions.assertFalse(farmRepository.existsById(id));
    }
}
