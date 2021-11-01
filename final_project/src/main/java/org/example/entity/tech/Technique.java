package org.example.entity.tech;

import org.example.entity.animal.Animal;
import org.example.entity.plant.Plant;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "techniques")
public class Technique {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, name = "type_of_technique")
    private String typeOfTechnique;
    
    @Column(nullable = false, name = "number_of_technique")
    private Integer numberOfTechnique;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Plant> plant;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Animal> animal;
    
    @ManyToMany(mappedBy = "techniques", fetch = FetchType.LAZY)
    private List<SparePart> spareParts;
    
    @ManyToMany(mappedBy = "techniques", fetch = FetchType.LAZY)
    private List<CombustibleLubricant> combustibleLubricants;
    
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

    public Integer getNumberOfTechnique() {
        return numberOfTechnique;
    }

    public void setNumberOfTechnique(Integer numberOfTechnique) {
        this.numberOfTechnique = numberOfTechnique;
    }

    public List<Plant> getPlant() {
        return plant;
    }

    public void setPlant(List<Plant> plant) {
        this.plant = plant;
    }

    public List<Animal> getAnimal() {
        return animal;
    }

    public void setAnimal(List<Animal> animal) {
        this.animal = animal;
    }

    public List<SparePart> getSpareParts() {
        return spareParts;
    }

    public void setSpareParts(List<SparePart> spareParts) {
        this.spareParts = spareParts;
    }

    public List<CombustibleLubricant> getCombustibleLubricants() {
        return combustibleLubricants;
    }

    public void setCombustibleLubricants(List<CombustibleLubricant> combustibleLubricants) {
        this.combustibleLubricants = combustibleLubricants;
    }
}
