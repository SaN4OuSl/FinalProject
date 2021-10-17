package org.example.service.impl;

import org.example.entity.Farm;
import org.example.repository.FarmRepository;
import org.example.service.FarmService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmServiceImpl implements FarmService {
    
    private final FarmRepository farmRepository;
    
    public FarmServiceImpl(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }
    
    @Override
    public void create(Farm farm) {
        farmRepository.save(farm);
    }
    
    @Override
    public void update(Farm farm) {
        farmRepository.save(farm);
    }
    
    @Override
    public boolean existById(Long id) {
        return farmRepository.existsById(id);
    }
    
    @Override
    public void delete(Long id) {
        farmRepository.deleteById(id);
    }
    
    @Override
    public Farm findById(Long id) {
        return farmRepository.findById(id).get();
    }
    
    @Override
    public List<Farm> findAll() {
        return farmRepository.findAll();
    }
}
