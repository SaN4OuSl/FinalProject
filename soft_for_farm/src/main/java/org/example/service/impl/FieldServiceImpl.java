package org.example.service.impl;

import org.example.entity.Farm;
import org.example.entity.plant.Field;
import org.example.repository.plant.FieldRepository;
import org.example.service.FieldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldServiceImpl implements FieldService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FarmServiceImpl.class);
    
    private final FieldRepository fieldRepository;
    
    public FieldServiceImpl(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }
    
    @Override
    public void addField(Farm farm, Field field) {
        if (field != null) {
            LOGGER.info("Start create field: " + field.getId());
            field.setFarm(farm);
            fieldRepository.save(field);
            LOGGER.info("End create field: " + field.getId());
        } else {
            LOGGER.warn("field is null!");
        }
    }
    
    @Override
    public void updateField(Long id, Field field) {
        if (fieldRepository.existsById(id)) {
            LOGGER.info("Start update field: " + id);
            Field newField = findFieldByCadastralNumber(id);
            newField.setCadastralNumber(field.getCadastralNumber());
            newField.setSizeOfField(field.getSizeOfField());
            newField.setRentalPrice(field.getRentalPrice());
            fieldRepository.save(newField);
            LOGGER.info("End update field: " + id);
        } else {
            LOGGER.warn("field doesn't exists");
        }
    }
    
    @Override
    public Field findFieldByCadastralNumber(Long id) {
        return fieldRepository.findById(id).orElse(null);
    }
    
    @Override
    public void deleteField(Long id) {
        if (fieldRepository.existsById(id)) {
            LOGGER.info("Start delete field: " + id);
            fieldRepository.deleteById(id);
            LOGGER.info("End delete field: " + id);
        } else {
            LOGGER.warn("field doesn't exists");
        }
    }
    
    @Override
    public Field findFieldById(Long id) {
        return fieldRepository.findById(id).orElse(null);
    }
    @Override
    public List<Field> findAllFields() {
        LOGGER.info("Read all fields");
        return fieldRepository.findAll();
    }
}
