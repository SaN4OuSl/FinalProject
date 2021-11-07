package org.example.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "animals")
public class Animal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, message = "Animal name must be have more than 2 characters")
    @Column(name = "animal_name")
    private String animalName;

    @NotNull
    @DecimalMin("0.00")
    @Column(name = "number_of_animals")
    private Integer numberOfAnimals;
    
    @NotNull
    @DecimalMin("0.00")
    @Column(name = "cost_of_feeds")
    private Double costOfFeeds;
    
    @NotNull
    @DecimalMin("0.00")
    @Column(name = "rental_price_if_building")
    private Double rentalPriceOfBuilding;
    
    @NotNull
    @DecimalMin("0.00")
    private Long costOfOneAnimal;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public Integer getNumberOfAnimals() {
        return numberOfAnimals;
    }

    public void setNumberOfAnimals(Integer numberOfAnimals) {
        this.numberOfAnimals = numberOfAnimals;
    }
    
    public Long getCostOfOneAnimal() {
        return costOfOneAnimal;
    }
    
    public void setCostOfOneAnimal(Long costOfOneAnimal) {
        this.costOfOneAnimal = costOfOneAnimal;
    }
    
    public Double getCostOfFeeds() {
        return costOfFeeds;
    }
    
    public void setCostOfFeeds(Double costOfFeeds) {
        this.costOfFeeds = costOfFeeds;
    }
    
    public Double getRentalPriceOfBuilding() {
        return rentalPriceOfBuilding;
    }
    
    public void setRentalPriceOfBuilding(Double rentalPriceOfBuilding) {
        this.rentalPriceOfBuilding = rentalPriceOfBuilding;
    }
}
