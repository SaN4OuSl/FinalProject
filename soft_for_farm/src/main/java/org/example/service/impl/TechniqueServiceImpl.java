package org.example.service.impl;


import org.example.entity.Technique;
import org.example.entity.User;
import org.example.exception.farm.AccessToFarmException;
import org.example.exception.farm.FarmNotFoundException;
import org.example.exception.technique.AccessToTechniqueException;
import org.example.exception.technique.TechniqueNotFoundException;
import org.example.exception.user.UserNotFoundException;
import org.example.repository.FarmRepository;
import org.example.repository.TechniqueRepository;
import org.example.service.TechniqueService;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechniqueServiceImpl implements TechniqueService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TechniqueServiceImpl.class);
    
    private final TechniqueRepository techniqueRepository;
    private final FarmRepository farmRepository;
    private final UserService userService;
    
    @Autowired
    public TechniqueServiceImpl(TechniqueRepository techniqueRepository, FarmRepository farmRepository, UserService userService) {
        this.techniqueRepository = techniqueRepository;
        this.farmRepository = farmRepository;
        this.userService = userService;
    }
    
    @Override
    public Technique addTechnique(Long farmId, Technique technique) throws AccessToFarmException, FarmNotFoundException, UserNotFoundException {
        if (!farmRepository.existsById(farmId)) {
            throw new FarmNotFoundException("Farm with this id not found");
        }
        User user = userService.getUserByAuthentication();
        if (!farmRepository.existsByIdAndUser(farmId, user)) {
            throw new AccessToFarmException("You don't have access to this farm");
        }
        LOGGER.info("Start create technique: " + technique.getId());
        technique.setFarm(farmRepository.findById(farmId).orElse(null));
        techniqueRepository.save(technique);
        LOGGER.info("End create technique: " + technique.getId());
        LOGGER.warn("technique is null!");
        return technique;
    }
    
    @Override
    public Technique updateTechnique(Long id, Technique technique) throws UserNotFoundException, TechniqueNotFoundException {
        LOGGER.info("Start update technique: " + id);
        Technique newTechnique = findTechniqueById(id);
        newTechnique.setTypeOfTechnique(technique.getTypeOfTechnique());
        newTechnique.setPriceOfLubricant(technique.getPriceOfLubricant());
        newTechnique.setPriceOfParts(technique.getPriceOfParts());
        techniqueRepository.save(newTechnique);
        LOGGER.info("End update technique: " + id);
        return newTechnique;
    }
    
    @Override
    public void deleteTechnique(Long id) throws UserNotFoundException, AccessToTechniqueException, TechniqueNotFoundException {
        if (techniqueRepository.existsById(id)) {
            User user = userService.getUserByAuthentication();
            if (checkAccessToTechnique(user, id)) {
                LOGGER.info("Start delete technique: " + id);
                techniqueRepository.deleteById(id);
                LOGGER.info("End delete technique: " + id);
            } else {
                LOGGER.warn("User dont have access to this technique");
                throw new AccessToTechniqueException("You dont have access to this technique");
            }
        } else {
            LOGGER.warn("Technique doesn't exists");
            throw new TechniqueNotFoundException("Technique doesn't exists");
        }
    }
    
    @Override
    public Technique findTechniqueById(Long id) throws UserNotFoundException, TechniqueNotFoundException {
        User user = userService.getUserByAuthentication();
        Technique technique = techniqueRepository.findByIdAndUser(id, user);
        if (technique != null)
            return technique;
        else throw new TechniqueNotFoundException("Technique with this user and id not found");
    }
    
    @Override
    public Double expensesCounter(Long id) {
        return techniqueRepository.countExpensesOfTechniqueById(id);
    }
    
    @Override
    public List<Technique> findAllTechniquesByFarm(Long farmId) throws FarmNotFoundException, AccessToFarmException, UserNotFoundException {
        LOGGER.info("Read all techniques by farm");
        if (!farmRepository.existsById(farmId)) {
            LOGGER.info("Farm with this id not found");
            throw new FarmNotFoundException("Farm with this id not found");
        }
        User user = userService.getUserByAuthentication();
        if (!farmRepository.existsByIdAndUser(farmId, user)) {
            LOGGER.info("User doesn't have access to this farm");
            throw new AccessToFarmException("You don't have access to this farm");
        }
        return techniqueRepository.findAllByFarmId(farmId);
    }
    
    private boolean checkAccessToTechnique(User user, Long id) {
        return techniqueRepository.existsByIdAndUser(id, user);
    }
}
