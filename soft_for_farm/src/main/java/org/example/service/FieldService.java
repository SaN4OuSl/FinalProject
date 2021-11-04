package org.example.service;

import org.example.entity.Farm;
import org.example.entity.plant.Field;

import java.util.List;

public interface FieldService {
    
    void addField(Farm farm, Field field);
    void updateField(Long id, Field field);
    Field findFieldByCadastralNumber(Long id);
    void deleteField(Farm farm,Long id);
    List<Field> findAllFields();
}
