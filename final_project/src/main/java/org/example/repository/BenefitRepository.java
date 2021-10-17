package org.example.repository;

import org.example.entity.plant.BenefitPlant;
import org.example.entity.plant.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenefitRepository extends JpaRepository<BenefitPlant, Long> {
    
    Plant findBenefitPlantByPlant (Plant plant);
}
