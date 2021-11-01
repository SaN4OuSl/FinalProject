package org.example.entity.plant;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "fertilizers")
public class Fertilizer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name_of_fertilizer")
    private String nameOfFertilizers;
    
    @Column(name = "name_of_measure")
    private String nameOfMeasure;

    @Column(name = "number_of_fertilizer")
    private Long numberOfFertilizers;

    @Column(name = "cost_of_fertilizer")
    private Long costOfFertilizers;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Plant> plant;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNameOfFertilizers() {
        return nameOfFertilizers;
    }

    public void setNameOfFertilizers(String nameOfFertilizers) {
        this.nameOfFertilizers = nameOfFertilizers;
    }

    public String getNameOfMeasure() {
        return nameOfMeasure;
    }

    public void setNameOfMeasure(String nameOfMeasure) {
        this.nameOfMeasure = nameOfMeasure;
    }

    public Long getNumberOfFertilizers() {
        return numberOfFertilizers;
    }

    public void setNumberOfFertilizers(Long numberOfFertilizers) {
        this.numberOfFertilizers = numberOfFertilizers;
    }

    public Long getCostOfFertilizers() {
        return costOfFertilizers;
    }

    public void setCostOfFertilizers(Long costOfFertilizers) {
        this.costOfFertilizers = costOfFertilizers;
    }

    public List<Plant> getPlant() {
        return plant;
    }

    public void setPlant(List<Plant> plant) {
        this.plant = plant;
    }
}
