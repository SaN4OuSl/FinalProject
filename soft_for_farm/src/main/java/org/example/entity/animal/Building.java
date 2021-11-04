package org.example.entity.animal;

import org.example.entity.Farm;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "buildings")
public class Building {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, name = "size_of_building")
    private Double sizeOfBuilding;

    @Column(name = "rental_price")
    private Double rentalPrice;

    @ManyToOne
    @JoinColumn(nullable = false,name = "farm_id")
    private Farm farm;

    @ManyToMany(mappedBy = "buildings", fetch = FetchType.LAZY)
    private List<Animal> animal;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
