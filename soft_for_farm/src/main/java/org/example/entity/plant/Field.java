package org.example.entity.plant;


import org.example.entity.Farm;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name = "fields")
public class Field {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NaturalId(mutable = true)
    @Column(nullable = false, name = "cadastral_number", unique = true)
    @Pattern(regexp = "^[0-9]{10}\\.[0-9]{2}\\.[0-9]{3}\\.[0-9]{4}$", message = "Cadastral number in wrong format")
    private String cadastralNumber;
    
    @Min(1)
    @Column(nullable = false, name = "size_of_field")
    private Double sizeOfField;
    
    @NotNull
    @Min(0)
    @Column(nullable = false, name = "rental_price")
    private Double rentalPrice;
    
    @ManyToOne
    @JoinColumn(nullable = false, name = "farm_id")
    private Farm farm;
    
    @ManyToMany(mappedBy = "field", fetch = FetchType.LAZY)
    private List<Plant> plants;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
