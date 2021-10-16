package org.example.entity.tech;

import org.example.entity.BaseEntity;
import org.example.entity.animal.Animals;
import org.example.entity.plant.Plants;
import org.example.entity.tech.CombustibleLubricants;
import org.example.entity.tech.SpareParts;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "techniques")
public class Techniques extends BaseEntity {
    
    @Column(nullable = false, name = "type_of_technique")
    private String typeOfTechnique;
    
    @Column(nullable = false, name = "number_of_technique")
    private Integer numberOfTechnique;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Plants> plant;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Animals> animal;
    
    @ManyToMany(mappedBy = "techniques", fetch = FetchType.LAZY)
    private List<SpareParts> spareParts;
    
    @ManyToMany(mappedBy = "techniques", fetch = FetchType.LAZY)
    private List<CombustibleLubricants> combustibleLubricants;

    public String getTypeOfTechnique() {
        return typeOfTechnique;
    }

    public void setTypeOfTechnique(String typeOfTechnique) {
        this.typeOfTechnique = typeOfTechnique;
    }

    public Integer getNumberOfTechnique() {
        return numberOfTechnique;
    }

    public void setNumberOfTechnique(Integer numberOfTechnique) {
        this.numberOfTechnique = numberOfTechnique;
    }

    public List<Plants> getPlant() {
        return plant;
    }

    public void setPlant(List<Plants> plant) {
        this.plant = plant;
    }

    public List<Animals> getAnimal() {
        return animal;
    }

    public void setAnimal(List<Animals> animal) {
        this.animal = animal;
    }

    public List<SpareParts> getSpareParts() {
        return spareParts;
    }

    public void setSpareParts(List<SpareParts> spareParts) {
        this.spareParts = spareParts;
    }

    public List<CombustibleLubricants> getCombustibleLubricants() {
        return combustibleLubricants;
    }

    public void setCombustibleLubricants(List<CombustibleLubricants> combustibleLubricants) {
        this.combustibleLubricants = combustibleLubricants;
    }
}
