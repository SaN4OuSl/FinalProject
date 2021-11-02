package org.example.repository.tech;

import org.example.entity.tech.Technique;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechniqueRepository extends JpaRepository<Technique, Long> {
}
