package org.example.repository.plant;

import org.example.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    
    boolean existsById(Long id);
    Optional<Plant> findById(Long id);
}
