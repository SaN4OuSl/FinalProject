package org.example.repository;

import org.example.entity.Farm;
import org.example.entity.Plant;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {

    List<Plant> findAllByFarm(Farm farm);
    
    @Query("select (count(p) > 0) from Plant p JOIN Farm f on (f.id = p.farm.id) JOIN User u on (u.id = f.user.id) where p.id = ?1 and u = ?2")
    boolean existsByIdAndUser(Long id, User user);
}
