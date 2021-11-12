package org.example.repository.auth;

import org.example.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    
    User findByLogin(String login);
    
    @Query("select u from User u")
    Page<User> findAllUsers(User user, Pageable pageable);
    
    boolean existsUserByLogin(String login);
}
