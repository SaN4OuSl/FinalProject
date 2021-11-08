package org.example.model;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "plants")
public class Plant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(min = 2, message = "Plant name must be more than 2 characters")
    @Column(name = "plant_name")
    private String plantName;
    
    @NotNull
    @DecimalMin("0.00")
    @Column(name = "size_of_field_for_plant")
    private Double sizeOfFieldForPlant;
    
    @NotNull
    @DecimalMin("0.00")
    @Column(nullable = false, name = "rental_price_of_field")
    private Double rentalPriceOfField;
    
    @NotNull
    @DecimalMin("0.00")
    @Column(name = "cost_of_fertilizers")
    private Double costOfFertilizers;
    
    @NotNull
    @DecimalMin("0.00")
    @Column(name = "other_expense")
    private Double otherExpense;
    
    @NotNull
    @DecimalMin("0.00")
    @Column(name = "plant_harvest")
    private Double plantHarvest;
    
    @NotNull
    @DecimalMin("0.00")
    @Column(name = "cost_of_plant")
    private Double costOfPlant;
    
    @ManyToOne
    @JoinColumn(nullable = false, name = "farm_id")
    private Farm farm;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getPlantName() {
        return plantName;
    }
    
    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }
    
    public Double getSizeOfFieldForPlant() {
        return sizeOfFieldForPlant;
    }
    
    public void setSizeOfFieldForPlant(Double sizeOfFieldForPlant) {
        this.sizeOfFieldForPlant = sizeOfFieldForPlant;
    }
    
    public Double getRentalPriceOfField() {
        return rentalPriceOfField;
    }
    
    public void setRentalPriceOfField(Double rentalPriceOfField) {
        this.rentalPriceOfField = rentalPriceOfField;
    }
    
    public Double getCostOfFertilizers() {
        return costOfFertilizers;
    }
    
    public void setCostOfFertilizers(Double costOfFertilizers) {
        this.costOfFertilizers = costOfFertilizers;
    }
    
    public Double getOtherExpense() {
        return otherExpense;
    }
    
    public void setOtherExpense(Double otherExpense) {
        this.otherExpense = otherExpense;
    }
    
    public Double getPlantHarvest() {
        return plantHarvest;
    }
    
    public void setPlantHarvest(Double plantHarvest) {
        this.plantHarvest = plantHarvest;
    }
    
    public Double getCostOfPlant() {
        return costOfPlant;
    }
    
    public void setCostOfPlant(Double costOfPlant) {
        this.costOfPlant = costOfPlant;
    }
    
    public Farm getFarm() {
        return farm;
    }
    
    public void setFarm(Farm farm) {
        this.farm = farm;
    }
}
