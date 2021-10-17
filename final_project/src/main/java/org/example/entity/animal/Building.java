package org.example.entity.animal;

import org.example.entity.BaseEntity;
import org.example.entity.Farm;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "buildings")
public class Building extends BaseEntity {

    @Column(nullable = false, name = "size_of_building")
    private Double sizeOfBuilding;

    @Column(name = "rental_price")
    private Double rentalPrice;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(nullable = false,name = "farm_id")
    private Farm farm;

    @ManyToMany(mappedBy = "buildings", fetch = FetchType.LAZY)
    private List<Animal> animal;

    public Double getSizeOfBuilding() {
        return sizeOfBuilding;
    }

    public void setSizeOfBuilding(Double sizeOfBuilding) {
        this.sizeOfBuilding = sizeOfBuilding;
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

    public List<Animal> getAnimal() {
        return animal;
    }

    public void setAnimal(List<Animal> animal) {
        this.animal = animal;
    }
}