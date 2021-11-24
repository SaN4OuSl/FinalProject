package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "animals")
public class Animal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "farm_id")
    @JsonIgnore
    private Farm farm;
    
    public Animal() {
    }
    
    public Animal(String animalName, Integer numberOfAnimals, Double costOfFeeds, Double rentalPriceOfBuilding, Double otherExpenses, Double costOfOneAnimal) {
        this.animalName = animalName;
        this.numberOfAnimals = numberOfAnimals;
        this.costOfFeeds = costOfFeeds;
        this.rentalPriceOfBuilding = rentalPriceOfBuilding;
        this.otherExpenses = otherExpenses;
        this.costOfOneAnimal = costOfOneAnimal;
    }
    
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
