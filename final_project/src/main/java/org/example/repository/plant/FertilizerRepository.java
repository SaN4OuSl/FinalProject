package org.example.repository.plant;

import org.example.entity.plant.Fertilizer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FertilizerRepository extends JpaRepository<Fertilizer, Long> {
}
