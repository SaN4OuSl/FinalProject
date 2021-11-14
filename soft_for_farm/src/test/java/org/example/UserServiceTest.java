package org.example;

import org.example.entity.User;
import org.example.repository.auth.UserRepository;
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
@Sql(scripts = {"create-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"delete-database.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        assertThat(userRepository).isNotNull();
        assertThat(userService).isNotNull();
    }

    @Test
    void registrationUser() throws Exception {
        User user = new User("username", "userpass");
        userService.registration(user);
        Long id = user.getId();

        Assertions.assertTrue(userRepository.existsById(id));
        userService.deleteById(id);
    }

    @Test
    void registrationAdmin() throws Exception {
        User userAdmin = userService.findByLogin("test user");
        User user = new User("username", "userpass");
        userService.registrationAdmin(userAdmin, user);
        Long id = user.getId();

        Assertions.assertTrue(userRepository.existsById(id));
        userService.deleteById(id);
    }

    @Test
    void deleteUser() throws Exception {
        User user = new User("username", "userpass");
        userService.registration(user);
        Long id = user.getId();
        userService.deleteById(id);
        Assertions.assertFalse(userRepository.existsById(id));
    }

    @Test
    void updateUser() throws Exception {
        User user = new User("username", "userpass");
        userService.registration(user);
        Long id = user.getId();
        User newUser = new User("username updated", "userpassupd");

        userService.updateUserById(id, newUser);
        Assertions.assertEquals(newUser.getLogin(), userService.findUserById(id).getLogin());
        userService.deleteById(id);
    }
}
