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
    @Column(name = "other_expenses")
    private Double otherExpenses;
    
    @NotNull
    @DecimalMin("0.00")
    private Double costOfOneAnimal;
    
    @ManyToOne
    @JoinColumn(nullable = false, name = "farm_id")
    private Farm farm;
    
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
    
    public Double getCostOfOneAnimal() {
        return costOfOneAnimal;
    }
    
    public void setCostOfOneAnimal(Double costOfOneAnimal) {
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
    
    public Double getOtherExpenses() {
        return otherExpenses;
    }
    
    public void setOtherExpenses(Double otherExpenses) {
        this.otherExpenses = otherExpenses;
    }
    
    public void setRentalPriceOfBuilding(Double rentalPriceOfBuilding) {
        this.rentalPriceOfBuilding = rentalPriceOfBuilding;
    }
    
    public Farm getFarm() {
        return farm;
    }
    
    public void setFarm(Farm farm) {
        this.farm = farm;
    }
}
