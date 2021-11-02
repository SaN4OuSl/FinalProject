package org.example.service.impl;

import org.example.entity.Farm;
import org.example.entity.plant.Field;
import org.example.repository.plant.FieldRepository;
import org.example.service.FieldService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldServiceImpl implements FieldService {
    
    private final FieldRepository fieldRepository;
    
    public FieldServiceImpl(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }
    
    @Override
    public void create(Field field) {
        fieldRepository.save(field);
    }
    
    @Override
    public void update(Field field) {
        fieldRepository.save(field);
    }
    
    @Override
    public boolean existByCadastral(String cadastralNumber) {
        return fieldRepository.existsByCadastralNumber(cadastralNumber);
    }
    
    @Override
    public void delete(String cadastralNumber) {
        fieldRepository.deleteByCadastralNumber(cadastralNumber);
    }
    
    @Override
    public Field findByCadastral(String cadastralNumber) {
        return fieldRepository.findByCadastralNumber(cadastralNumber);
    }
    
    @Override
    public List<Field> findAllFieldsByFarm(Farm farm) {
        return fieldRepository.findAllByFarm(farm);
    }
}
