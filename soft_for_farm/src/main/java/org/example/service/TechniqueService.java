package org.example.service;

import org.example.entity.Technique;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.exception.technique.AccessToTechniqueException;
import org.example.exception.technique.TechniqueNotFoundException;
import org.example.exception.user.UserNotFoundException;

import java.util.List;

public interface TechniqueService {
    
    Technique addTechnique(Long farmId, Technique technique)  throws AccessToFarmException, FarmNotFoundException, UserNotFoundException;
    
    Technique updateTechnique(Long id, Technique technique) throws UserNotFoundException, TechniqueNotFoundException;
    
    void deleteTechnique(Long id) throws UserNotFoundException, AccessToTechniqueException, TechniqueNotFoundException;
    
    Technique findTechniqueById(Long id) throws UserNotFoundException, TechniqueNotFoundException;
    
    Double expensesCounter(Long id);
    
    List<Technique> findAllTechniquesByFarm(Long farmId) throws FarmNotFoundException, AccessToFarmException, UserNotFoundException;
}
