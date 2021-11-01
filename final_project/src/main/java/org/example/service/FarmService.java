package org.example.service;

import org.example.entity.Farm;

import java.util.List;

public interface FarmService {
    
    void create(Farm farm);
    void update(Farm farm);
    boolean existById(Long id);
    void delete(Long id);
    Farm findById(Long id);
    List<Farm> findAll();
}
