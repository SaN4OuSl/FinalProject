package org.example.repository;

import org.example.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {
    
    Optional<Farm> findByFarmName(String farmName);
    
    Boolean existsByFarmName(String farmName);
}
