package org.example.repository;

import org.example.entity.Farms;
import org.example.entity.animal.Buildings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildingsRepository extends JpaRepository<Buildings, Long> {
    
    List<Buildings> findAllByFarm(Farms farms);
}
