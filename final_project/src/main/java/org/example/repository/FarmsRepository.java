package org.example.repository;

import org.example.entity.Farms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FarmsRepository extends JpaRepository<Farms, Long> {
    
    Optional<Farms> findByFarmName(String farmName);
    
    Boolean existsByFarmName(String farmName);
}
