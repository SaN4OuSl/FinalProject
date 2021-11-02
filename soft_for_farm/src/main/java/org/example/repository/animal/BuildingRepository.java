package org.example.repository.animal;

import org.example.entity.Farm;
import org.example.entity.animal.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {
    
    List<Building> findAllByFarm(Farm farm);
}
