package org.example.entity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "techniques")
public class Technique {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(nullable = false, name = "type_of_technique")
    private String typeOfTechnique;
    
    @NotNull
    @DecimalMin("0.00")
    @Column(name = "price_of_parts")
    private Double priceOfParts;
    
    @NotNull
    @DecimalMin("0.00")
    @Column(name = "price_of_lubricants")
    private Double priceOfLubricant;
    
    @ManyToOne
    @JoinColumn(nullable = false, name = "farm_id")
    private Farm farm;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTypeOfTechnique() {
        return typeOfTechnique;
    }
    
    public void setTypeOfTechnique(String typeOfTechnique) {
        this.typeOfTechnique = typeOfTechnique;
    }
    
    public Double getPriceOfParts() {
        return priceOfParts;
    }
    
    public void setPriceOfParts(Double priceOfParts) {
        this.priceOfParts = priceOfParts;
    }
    
    public Double getPriceOfLubricant() {
        return priceOfLubricant;
    }
    
    public void setPriceOfLubricant(Double priceOfLubricant) {
        this.priceOfLubricant = priceOfLubricant;
    }
    
    public Farm getFarm() {
        return farm;
    }
    
    public void setFarm(Farm farm) {
        this.farm = farm;
    }
}
