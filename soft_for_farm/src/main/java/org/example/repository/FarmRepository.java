package org.example.repository;

import org.example.entity.Farm;
import org.example.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {
    
    Page<Farm> findAllByYearOfStatisticAndUser(@Pattern(regexp = "^\\d+$", message = "Year must only contain numbers") String yearOfStatistic, User user, Pageable pageable);
    
    Page<Farm> findAllByFarmNameAndUser(@Size(min = 2, message = "Farm name must be more than 2 characters") String farmName, User user, Pageable pageable);
    
    Page<Farm> findAllByUser(User user, Pageable pageable);
    
    boolean existsByIdAndUser(Long id, User user);
}
