package org.example.repository.plant;

import org.example.entity.plant.BenefitPlant;
import org.example.entity.plant.Field;
import org.example.entity.plant.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    
    List<Plant> findAllByField(Field field);
    Plant findPlantsByBenefitPlants (BenefitPlant benefitPlant);
}
