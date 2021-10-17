package org.example.repository;

import org.example.entity.Farms;
import org.example.entity.plant.Fields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldsRepository extends JpaRepository<Fields, Long> {
    
    List<Fields> findAllByFarm(Farms farms);
}
