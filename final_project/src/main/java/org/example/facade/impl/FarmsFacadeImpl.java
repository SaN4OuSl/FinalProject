package org.example.facade.impl;

import org.example.dto.request.FarmDtoRequest;
import org.example.entity.Farms;
import org.example.facade.FarmsFacade;
import org.example.service.FarmsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmsFacadeImpl implements FarmsFacade {
    
    private final FarmsService farmsService;
    
    public FarmsFacadeImpl(FarmsService farmsService) {
        this.farmsService = farmsService;
    }
    
    @Override
    public void create(FarmDtoRequest dtoRequest) {
        Farms farms = new Farms();
        farms.setFarmName(dtoRequest.getFarmName());
        farms.setAddress(dtoRequest.getAddress());
        farms.setYearOfStatistic(dtoRequest.getYearOfStatistic());
        farmsService.create(farms);
    }
    
    @Override
    public void update(FarmDtoRequest dtoRequest, Long id) {
        Farms farms = farmsService.findById(id);
        farms.setFarmName(dtoRequest.getFarmName());
        farms.setAddress(dtoRequest.getAddress());
        farms.setYearOfStatistic(dtoRequest.getYearOfStatistic());
        farmsService.update(farms);
    }
    
    @Override
    public void delete(Long id) {
        if(farmsService.existById(id))
            farmsService.delete(id);
    }
    
    @Override
    public Farms findById(Long id) {
        return farmsService.findById(id);
    }
    
    @Override
    public List<Farms> findAll() {
        return farmsService.findAll();
    }
}
