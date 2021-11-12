package org.example.repository;

import org.example.entity.Farm;
import org.example.entity.Technique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechniqueRepository extends JpaRepository<Technique, Long> {
    List<Technique> findAllByFarm(Farm farm);
}
