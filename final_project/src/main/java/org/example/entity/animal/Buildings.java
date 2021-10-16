package org.example.entity.animal;

import org.example.entity.BaseEntity;
import org.example.entity.Farms;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "buildings")
public class Buildings extends BaseEntity {

    @Column(nullable = false, name = "size_of_building")
    private Double sizeOfBuilding;

    @Column(name = "rental_price")
    private Double rentalPrice;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(nullable = false,name = "farm_id")
    private Farms farm;

    @ManyToMany(mappedBy = "buildings", fetch = FetchType.LAZY)
    private List<Animals> animal;

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

    public Farms getFarm() {
        return farm;
    }

    public void setFarm(Farms farm) {
        this.farm = farm;
    }

    public List<Animals> getAnimal() {
        return animal;
    }

    public void setAnimal(List<Animals> animal) {
        this.animal = animal;
    }
}
