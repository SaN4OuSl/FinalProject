package org.example.service;

import org.example.entity.Farm;
import org.example.entity.User;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FarmService {
    
    void addFarm(User user, Farm farm);
    
    void updateFarm(User user, Long id, Farm farm) throws FarmNotFoundException, AccessToFarmException;
    
    Farm findFarmById(User user, Long id) throws FarmNotFoundException, AccessToFarmException;
    
    void deleteFarm(User user, Long id);
    
    Double profitCounter(Farm farm);
    
    Double expensesCounter(Farm farm);
    
    Double netProfitCounter(Farm farm);
    
    Page<Farm> findFarmsByYear(String year, User user, Pageable pageable);
    
    Page<Farm> findFarmsByFarmName(String farmName, User user, Pageable pageable);
    
    Page<Farm> findAllPageable(User user, Pageable pageable);
}
