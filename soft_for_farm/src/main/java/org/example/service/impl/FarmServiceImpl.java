package org.example.service.impl;

import org.example.entity.Farm;
import org.example.entity.auth.User;
import org.example.repository.FarmRepository;
import org.example.service.FarmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmServiceImpl implements FarmService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FarmServiceImpl.class);
    
    private final FarmRepository farmRepository;
    
    @Autowired
    public FarmServiceImpl(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }
    
    public void addFarm(User user, Farm farm) {
        if (farm != null) {
            LOGGER.info("Start create farm: " + farm.getFarmName());
            farm.setUser(user);
            farmRepository.save(farm);
            LOGGER.info("End create farm: " + farm.getFarmName());
        } else {
            LOGGER.warn("Farm is null!");
        }
    }
    
    public void updateFarm(Long id, Farm farm) {
        if (farmRepository.existsById(id)) {
            LOGGER.info("Start update farm: " + id);
            Farm newFarm = findFarmById(id);
            newFarm.setFarmName(farm.getFarmName());
            newFarm.setAddress(farm.getAddress());
            newFarm.setYearOfStatistic(farm.getYearOfStatistic());
            farmRepository.save(newFarm);
            LOGGER.info("End update farm: " + id);
        } else {
            LOGGER.warn("farm doesn't exists");
        }
    }
    
    public Farm findFarmById(Long id) {
        return farmRepository.findById(id).orElse(null);
    }
    
    public void deleteFarm(User user, Long id) {
        if (farmRepository.existsById(id)) {
            LOGGER.info("Start delete farm: " + id);
            farmRepository.deleteById(id);
            LOGGER.info("End delete farm: " + id);
        } else {
            LOGGER.warn("farm doesn't exists");
        }
    }
    
    
    public List<Farm> findAllFarms() {
        LOGGER.info("Read all farms");
        return farmRepository.findAll();
    }
}
