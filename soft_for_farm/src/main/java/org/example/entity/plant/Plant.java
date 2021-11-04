package org.example.entity.plant;

import org.example.entity.tech.Technique;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "plants")
public class Plant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "plant_name")
    private String plantName;
    
    @Column(name = "size_of_field_for_plant")
    private Integer sizeOfFieldForPlant;

    @OneToOne(mappedBy = "plant", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private BenefitPlant benefitPlants;

    @ManyToMany(mappedBy = "plant", fetch = FetchType.LAZY)
    private List<Fertilizer> fertilizers;
    
    @ManyToMany(mappedBy = "plant", fetch = FetchType.LAZY)
    private List<Technique> techniques;
    
    @ManyToMany( fetch = FetchType.LAZY)
    private List<Field> field;
    
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

    public Integer getSizeOfFieldForPlant() {
        return sizeOfFieldForPlant;
    }

    public void setSizeOfFieldForPlant(Integer sizeOfFieldForPlant) {
        this.sizeOfFieldForPlant = sizeOfFieldForPlant;
    }

    public BenefitPlant getBenefitPlants() {
        return benefitPlants;
    }

    public void setBenefitPlants(BenefitPlant benefitPlants) {
        this.benefitPlants = benefitPlants;
    }

    public List<Fertilizer> getFertilizers() {
        return fertilizers;
    }

    public void setFertilizers(List<Fertilizer> fertilizers) {
        this.fertilizers = fertilizers;
    }

    public List<Technique> getTechniques() {
        return techniques;
    }

    public void setTechniques(List<Technique> techniques) {
        this.techniques = techniques;
    }

    public List<Field> getField() {
        return field;
    }

    public void setField(List<Field> field) {
        this.field = field;
    }
}
