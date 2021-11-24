package org.example.repository;

import org.example.entity.Animal;
import org.example.entity.Farm;
import org.example.entity.Plant;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    
    @Query("select p from Plant p where p.farm.id = ?1")
    List<Plant> findAllByFarmId(Long id);
    
    @Query("select (count(p) > 0) from Plant p JOIN Farm f on (f.id = p.farm.id) JOIN User u on (u.id = f.user.id) where p.id = ?1 and u = ?2")
    boolean existsByIdAndUser(Long id, User user);
    
    @Query("select p from Plant p JOIN Farm f on (f.id = p.farm.id) JOIN User u on (u.id = f.user.id) where p.id = ?1 and u = ?2")
    Plant findByIdAndUser(Long id, User user);
    
    @Query("select p.costOfPlant * p.plantHarvest * p.sizeOfFieldForPlant from Plant p where p.id = ?1")
    Double countProfitOfPlantById(Long id);
    
    @Query("select p.rentalPriceOfField + p.otherExpense + p.costOfFertilizers from Plant p where p.id = ?1")
    Double countExpensesOfPlantById(Long id);
}
