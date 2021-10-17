package org.example.entity.plant;


import org.example.entity.Farm;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "fields")
public class Field {
    
    @Id
    @NaturalId
    @Column(nullable = false, name = "cadastral_number")
    private String cadastralNumber;
    
    @Column(nullable = false, name = "size_of_field")
    private Double sizeOfField;
    
    @Column(name = "rental_price")
    private Double rentalPrice;
    
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(nullable = false,name = "farm_id")
    private Farm farm;
    
    @ManyToMany(mappedBy = "field", fetch = FetchType.LAZY)
    private List<Plant> plants;

    public String getCadastralNumber() {
        return cadastralNumber;
    }

    public void setCadastralNumber(String cadastralNumber) {
        this.cadastralNumber = cadastralNumber;
    }

    public Double getSizeOfField() {
        return sizeOfField;
    }

    public void setSizeOfField(Double sizeOfField) {
        this.sizeOfField = sizeOfField;
    }

    public Double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(Double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }
}