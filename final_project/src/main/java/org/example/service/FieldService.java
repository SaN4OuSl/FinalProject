package org.example.service;


import org.example.entity.Farm;
import org.example.entity.plant.Field;

import java.util.List;

public interface FieldService {
    
    void create(Field field);
    void update(Field field);
    boolean existByCadastral(String cadastralNumber);
    void delete(String cadastralNumber);
    Field findByCadastral(String cadastralNumber);
    List<Field> findAllFieldsByFarm(Farm farm);
}
