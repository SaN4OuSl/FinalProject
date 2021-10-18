package org.example.facade;


import org.example.dto.request.plant.FieldDtoRequest;
import org.example.entity.Farm;
import org.example.entity.plant.Field;

import java.util.List;

public interface FieldFacade {
    
    void create(FieldDtoRequest dtoRequest);
    void update(FieldDtoRequest dtoRequest, String cadastralNumber);
    void delete(String cadastralNumber);
    Field findByCadastralNumber(String cadastralNumber);
    List<Field> findAllFieldsByFarm(Farm farm);
}
