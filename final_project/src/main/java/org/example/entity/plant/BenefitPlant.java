package org.example.entity.plant;

import org.example.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "benefit_plants")
public class BenefitPlant extends BaseEntity {

    @Column(name = "plant_harvest")
    private Long plantHarvest;

    @Column(name = "cost_of_plant")
    private Long costOfPlant;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "plant_id")
    private Plants plant;

    public Long getPlantHarvest() {
        return plantHarvest;
    }

    public void setPlantHarvest(Long plantHarvest) {
        this.plantHarvest = plantHarvest;
    }

    public Long getCostOfPlant() {
        return costOfPlant;
    }

    public void setCostOfPlant(Long costOfPlant) {
        this.costOfPlant = costOfPlant;
    }

    public Plants getPlant() {
        return plant;
    }

    public void setPlant(Plants plant) {
        this.plant = plant;
    }
}
