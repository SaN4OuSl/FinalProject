package org.example.facade;

import org.example.dto.request.FarmDtoRequest;
import org.example.entity.Farms;

import java.util.List;

public interface FarmsFacade {
    
    void create(FarmDtoRequest dtoRequest);
    void update(FarmDtoRequest dtoRequest, Long Id);
    void delete(Long id);
    Farms findById(Long id);
    List<Farms> findAll();
}
