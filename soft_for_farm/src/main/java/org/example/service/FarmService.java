package org.example.service;

import org.example.entity.Farm;
import org.example.entity.auth.User;

import java.util.List;

public interface FarmService {
    
    void addFarm(User user, Farm farm);
    void updateFarm(Long id, Farm farm);
    Farm findFarmById(Long id);
    void deleteFarm(User user, Long id);
    List<Farm> findAllFarms();
}
