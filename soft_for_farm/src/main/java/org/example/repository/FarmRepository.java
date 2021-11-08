package org.example.repository;

import org.example.model.Farm;
import org.example.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {
    
    List<Farm> findAllByYearOfStatisticAndUser(@Pattern(regexp = "^\\d+$", message = "Year must only contain numbers") String yearOfStatistic, User user);
    
    List<Farm> findAllByFarmNameAndUser(@Size(min = 2, message = "Farm name must be more than 2 characters") String farmName, User user);
    
    Page<Farm> findAllByUser(User user, Pageable pageable);
}
