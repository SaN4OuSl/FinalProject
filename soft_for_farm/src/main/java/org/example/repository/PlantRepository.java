package org.example.repository;

import org.example.entity.Farm;
import org.example.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {

    List<Plant> findAllByFarm(Farm farm);
}
