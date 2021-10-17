package org.example.service.impl;

import org.example.entity.Farms;
import org.example.repository.FarmsRepository;
import org.example.service.FarmsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmsServiceImpl implements FarmsService {
    
    private final FarmsRepository farmsRepository;
    
    public FarmsServiceImpl(FarmsRepository farmsRepository) {
        this.farmsRepository = farmsRepository;
    }
    
    @Override
    public void create(Farms farms) {
        farmsRepository.save(farms);
    }
    
    @Override
    public void update(Farms farms) {
        farmsRepository.save(farms);
    }
    
    @Override
    public boolean existById(Long id) {
        return farmsRepository.existsById(id);
    }
    
    @Override
    public void delete(Long id) {
        farmsRepository.deleteById(id);
    }
    
    @Override
    public Farms findById(Long id) {
        return farmsRepository.findById(id).get();
    }
    
    @Override
    public List<Farms> findAll() {
        return farmsRepository.findAll();
    }
}
