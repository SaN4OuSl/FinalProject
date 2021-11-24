package org.example.service;

import org.example.entity.Farm;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.exception.user.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FarmService {
    
    void addFarm(Farm farm) throws UserNotFoundException;
    
    void updateFarm(Long id, Farm farm) throws FarmNotFoundException, AccessToFarmException, UserNotFoundException;
    
    Farm findFarmById(Long id) throws FarmNotFoundException, AccessToFarmException, UserNotFoundException;
    
    void deleteFarm(Long id) throws AccessToFarmException, FarmNotFoundException, UserNotFoundException;
    
    Double profitCounter(Farm farm);
    
    Double expensesCounter(Farm farm);
    
    Double netProfitCounter(Farm farm);
    
    Page<Farm> findFarmsByYear(String year, Pageable pageable) throws UserNotFoundException;
    
    Page<Farm> findFarmsByFarmName(String farmName, Pageable pageable) throws UserNotFoundException;
    
    Page<Farm> findAllPageable(Pageable pageable) throws UserNotFoundException;
}
