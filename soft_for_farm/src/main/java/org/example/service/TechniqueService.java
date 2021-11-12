package org.example.service;

import org.example.entity.Farm;
import org.example.entity.Technique;

import java.util.List;

public interface TechniqueService {
    
    void addTechnique(Farm farm, Technique technique);
    
    void updateTechnique(Long id, Technique technique);
    
    void deleteTechnique(Long id);
    
    Technique findTechniqueById(Long id);
    
    Double expensesCounter(Technique technique);
    
    List<Technique> findAllTechniquesByFarm(Farm farm);
}
