package org.example.repository.auth;

import org.example.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
    User findByLogin(String login);
    
    boolean existsUserByLogin(String login);
}
