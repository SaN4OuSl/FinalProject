package org.example.entity.plant;

import javax.persistence.*;

@Entity
@Table(name = "benefit_plants")
public class BenefitPlant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "plant_harvest")
    private Long plantHarvest;

    @Column(name = "cost_of_plant")
    private Long costOfPlant;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "plant_id")
    private Plant plant;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }
}
