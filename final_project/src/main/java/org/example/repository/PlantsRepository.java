package org.example.repository;

import org.example.entity.plant.BenefitPlant;
import org.example.entity.plant.Plants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantsRepository extends JpaRepository<Plants, Long> {
    
    BenefitPlant findPlantsByBenefitPlants (BenefitPlant benefitPlant);
}
