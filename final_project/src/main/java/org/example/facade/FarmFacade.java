package org.example.facade;

import org.example.dto.request.FarmDtoRequest;
import org.example.entity.Farm;

import java.util.List;

public interface FarmFacade {
    
    void create(FarmDtoRequest dtoRequest);
    void update(FarmDtoRequest dtoRequest, Long Id);
    void delete(Long id);
    Farm findById(Long id);
    List<Farm> findAll();
}
