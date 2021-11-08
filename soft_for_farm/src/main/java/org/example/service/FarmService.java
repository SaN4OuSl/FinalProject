package org.example.service;

import org.example.entity.Farm;
import org.example.entity.auth.User;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;

public interface FarmService {
    
    void addFarm(User user, Farm farm);
    
    void updateFarm(Principal principal, Long id, Farm farm) throws FarmNotFoundException, AccessToFarmException;
    
    Farm findFarmById(Principal principal, Long id) throws FarmNotFoundException, AccessToFarmException;
    
    void deleteFarm(User user, Long id);
    
    Double profitCounter(Farm farm);
    
    Double expensesCounter(Farm farm);
    
    Double netProfitCounter(Farm farm);
    
    List<Farm> findFarmsByYear(String year, User user);
    
    List<Farm> findFarmsByFarmName(String farmName, User user);
    
    Page<Farm> findAllPageable(User user, Pageable pageable);
}
