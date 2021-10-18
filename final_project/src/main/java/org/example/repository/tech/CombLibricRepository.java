package org.example.repository.tech;

import org.example.entity.tech.CombustibleLubricant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CombLibricRepository extends JpaRepository<CombustibleLubricant, Long> {
}
