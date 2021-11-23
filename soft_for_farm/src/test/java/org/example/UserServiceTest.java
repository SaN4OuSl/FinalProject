package org.example;

import org.example.entity.User;
import org.example.repository.auth.UserRepository;
import org.example.service.UserService;
import org.example.service.impl.UserDetailsServiceImpl;
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
@Sql(scripts = {"create-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"delete-database.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;
    
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    
    @Test
    void contextLoads() {
        assertThat(userRepository).isNotNull();
        assertThat(userService).isNotNull();
        assertThat(userDetailsService).isNotNull();
    }

    @Test
    void registrationUser() throws Exception {
        User user = new User("username", "userpass");
        userService.registration(user);
        Long id = user.getId();

        Assertions.assertTrue(userRepository.existsById(id));
        userService.deleteById(user, id);
    }

    @Test
    void registrationAdmin() throws Exception {
        User user = new User("username", "userpass");
        userService.registrationAdmin(user);
        Long id = user.getId();

        Assertions.assertTrue(userRepository.existsById(id));
        userService.deleteById(user, id);
    }

    @Test
    void deleteUser() throws Exception {
        User user = new User("username", "userpass");
        userService.registration(user);
        Long id = user.getId();
        userService.deleteById(user, id);
        Assertions.assertFalse(userRepository.existsById(id));
    }

    @Test
    void updateUser() throws Exception {
        User user = new User("username", "userpass");
        userService.registration(user);
        Long id = user.getId();
        User newUser = new User("username updated", "userpassupd");

        userService.updateUserById(id, newUser, user);
        Assertions.assertEquals(newUser.getLogin(), userService.findUserById(id).getLogin());
        userService.deleteById(userService.findUserById(id), id);
    }
    
    @Test
    void addAdminRole() throws Exception {
        User user = new User("username", "userpass");
        userService.registration(user);
        Long id = user.getId();
        
        userService.addAdminRole(user);
        Assertions.assertTrue(userDetailsService.isAdmin(user));
        userService.deleteById(userService.findUserById(id), id);
    }
}
