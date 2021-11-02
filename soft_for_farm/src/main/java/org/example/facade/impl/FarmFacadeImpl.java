package org.example.facade.impl;

import org.example.dto.request.FarmDtoRequest;
import org.example.entity.Farm;
import org.example.facade.FarmFacade;
import org.example.service.FarmService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmFacadeImpl implements FarmFacade {
    
    private final FarmService farmService;
    
    public FarmFacadeImpl(FarmService farmService) {
        this.farmService = farmService;
    }
    
    @Override
    public void create(FarmDtoRequest dtoRequest) {
        Farm farm = new Farm();
        farm.setFarmName(dtoRequest.getFarmName());
        farm.setAddress(dtoRequest.getAddress());
        farm.setYearOfStatistic(dtoRequest.getYearOfStatistic());
        farmService.create(farm);
    }
    
    @Override
    public void update(FarmDtoRequest dtoRequest, Long id) {
        Farm farm = farmService.findById(id);
        farm.setFarmName(dtoRequest.getFarmName());
        farm.setAddress(dtoRequest.getAddress());
        farm.setYearOfStatistic(dtoRequest.getYearOfStatistic());
        farmService.update(farm);
    }
    
    @Override
    public void delete(Long id) {
        if(farmService.existById(id))
            farmService.delete(id);
    }
    
    @Override
    public Farm findById(Long id) {
        return farmService.findById(id);
    }
    
    @Override
    public List<Farm> findAll() {
        return farmService.findAll();
    }
}
