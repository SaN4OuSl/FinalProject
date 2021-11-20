package org.example.service.impl;


import org.example.entity.Farm;
import org.example.entity.Technique;
import org.example.entity.User;
import org.example.repository.TechniqueRepository;
import org.example.service.TechniqueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechniqueServiceImpl implements TechniqueService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TechniqueServiceImpl.class);
    
    private final TechniqueRepository techniqueRepository;
    
    @Autowired
    public TechniqueServiceImpl(TechniqueRepository techniqueRepository) {
        this.techniqueRepository = techniqueRepository;
    }
    
    @Override
    public void addTechnique(Farm farm, Technique technique) {
        if (technique != null) {
            LOGGER.info("Start create technique: " + technique.getId());
            technique.setFarm(farm);
            techniqueRepository.save(technique);
            LOGGER.info("End create technique: " + technique.getId());
        } else {
            LOGGER.warn("technique is null!");
        }
    }
    
    @Override
    public void updateTechnique(Long id, Technique technique) {
        if (techniqueRepository.existsById(id)) {
            LOGGER.info("Start update technique: " + id);
            Technique newTechnique = findTechniqueById(id);
            newTechnique.setTypeOfTechnique(technique.getTypeOfTechnique());
            newTechnique.setPriceOfLubricant(technique.getPriceOfLubricant());
            newTechnique.setPriceOfParts(technique.getPriceOfParts());
            techniqueRepository.save(newTechnique);
            LOGGER.info("End update technique: " + id);
        } else {
            LOGGER.warn("technique doesn't exists");
        }
    }
    
    @Override
    public void deleteTechnique(Long id) {
        if (techniqueRepository.existsById(id)) {
            LOGGER.info("Start delete technique: " + id);
            techniqueRepository.deleteById(id);
            LOGGER.info("End delete technique: " + id);
        } else {
            LOGGER.warn("technique doesn't exists");
        }
    }
    
    @Override
    public Technique findTechniqueById(Long id) {
        return techniqueRepository.findById(id).orElse(null);
    }
    
    @Override
    public Double expensesCounter(Technique technique) {
        return technique.getPriceOfLubricant() + technique.getPriceOfParts();
    }
    
    @Override
    public List<Technique> findAllTechniquesByFarm(Farm farm) {
        LOGGER.info("Read all techniques by farm");
        return techniqueRepository.findAllByFarm(farm);
    }
    
    @Override
    public boolean checkAccessToTechnique(User user, Long id) {
        return techniqueRepository.existsByIdAndUser(id, user);
    }
}
