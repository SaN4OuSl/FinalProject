package org.example.service;

import org.example.entity.Farm;
import org.example.entity.auth.User;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;

import java.security.Principal;
import java.util.List;

public interface FarmService {
    
    void addFarm(User user, Farm farm);
    void updateFarm(Principal principal, Long id, Farm farm) throws FarmNotFoundException, AccessToFarmException;
    Farm findFarmById(Principal principal, Long id) throws FarmNotFoundException, AccessToFarmException;
    void deleteFarm(User user, Long id);
    List<Farm> findAllFarms();
}
