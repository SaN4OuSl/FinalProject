package org.example.repository;

import org.example.entity.Farm;
import org.example.entity.Technique;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechniqueRepository extends JpaRepository<Technique, Long> {
    List<Technique> findAllByFarm(Farm farm);
    
    @Query("select (count(t) > 0) from Technique t JOIN Farm f on (f.id = t.farm.id) JOIN User u on (u.id = f.user.id) where t.id = ?1 and u = ?2")
    boolean existsByIdAndUser(Long id, User user);
    
    @Query("select t.priceOfLubricant + t.priceOfParts from Technique t where t.id = ?1")
    Double countExpensesOfTechniqueById(Long id);
}
