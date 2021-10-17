package org.example.repository;

import org.example.entity.Farm;
import org.example.entity.plant.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
    
    List<Field> findAllByFarm(Farm farm);
}
