package org.example.repository;

import org.example.entity.Animal;
import org.example.entity.Farm;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findAllByFarm(Farm farm);
    
    @Query("select (count(a) > 0) from Animal a JOIN Farm f on (f.id = a.farm.id) JOIN User u on (u.id = f.user.id) where a.id = ?1 and u = ?2")
    boolean existsByIdAndUser(Long id, User user);
    
    @Query("select a.costOfOneAnimal * a.numberOfAnimals from Animal a where a.id = ?1")
    Double countProfitOfAnimalById(Long id);
    
    @Query("select a.rentalPriceOfBuilding + a.costOfFeeds + a.otherExpenses from Animal a where a.id = ?1")
    Double countExpensesOfAnimalById(Long id);
}
