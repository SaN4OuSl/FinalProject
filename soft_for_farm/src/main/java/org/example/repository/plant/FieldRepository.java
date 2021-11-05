package org.example.repository.plant;

import org.example.entity.Farm;
import org.example.entity.plant.Field;
import org.example.entity.plant.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FieldRepository extends JpaRepository<Field, String> {
    
    List<Field> findAllByPlants(Plant plant);
    List<Field> findAllByFarm(Farm farm);
    Boolean existsByCadastralNumber(String cadastralNumber);
    void deleteByCadastralNumber(String cadastralNumber);
    Field findByCadastralNumber(String cadastralNumber);
    boolean existsById(Long id);
    Optional<Field> findById(Long id);
    void deleteById(Long id);
}
