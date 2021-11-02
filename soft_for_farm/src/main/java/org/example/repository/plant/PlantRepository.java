package org.example.repository.plant;

import org.example.entity.plant.BenefitPlant;
import org.example.entity.plant.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    
    BenefitPlant findPlantsByBenefitPlants (BenefitPlant benefitPlant);
}
