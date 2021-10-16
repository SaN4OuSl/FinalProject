package org.example.entity.plant;

import org.example.entity.*;
import org.example.entity.tech.Techniques;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "plants")
public class Plants extends BaseEntity {

    @Column(name = "plant_name")
    private String plantName;
    
    @Column(name = "size_of_field_for_plant")
    private Integer sizeOfFieldForPlant;

    @OneToOne(mappedBy = "plant", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private BenefitPlant benefitPlants;

    @ManyToMany(mappedBy = "plant", fetch = FetchType.LAZY)
    private List<Fertilizers> fertilizers;
    
    @ManyToMany(mappedBy = "plant", fetch = FetchType.LAZY)
    private List<Techniques> techniques;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Fields> field;

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

    public List<Fertilizers> getFertilizers() {
        return fertilizers;
    }

    public void setFertilizers(List<Fertilizers> fertilizers) {
        this.fertilizers = fertilizers;
    }

    public List<Techniques> getTechniques() {
        return techniques;
    }

    public void setTechniques(List<Techniques> techniques) {
        this.techniques = techniques;
    }

    public List<Fields> getField() {
        return field;
    }

    public void setField(List<Fields> field) {
        this.field = field;
    }
}
