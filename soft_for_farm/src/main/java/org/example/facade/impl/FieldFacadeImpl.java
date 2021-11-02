package org.example.facade.impl;

import org.example.dto.request.plant.FieldDtoRequest;
import org.example.entity.Farm;
import org.example.entity.plant.Field;
import org.example.facade.FieldFacade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldFacadeImpl implements FieldFacade {
    
    @Override
    public void create(FieldDtoRequest dtoRequest) {
        Field field = new Field();
        field.setCadastralNumber(dtoRequest.getСadastralNumber());
        field.setRentalPrice(dtoRequest.getRentalPrice());
        field.setSizeOfField(dtoRequest.getSizeOfField());
    }
    
    @Override
    public void update(FieldDtoRequest dtoRequest, String cadastralNumber) {
    
    }
    
    @Override
    public void delete(String cadastralNumber) {
    
    }
    
    @Override
    public Field findByCadastralNumber(String cadastralNumber) {
        return null;
    }
    
    @Override
    public List<Field> findAllFieldsByFarm(Farm farm) {
        return null;
    }
}
