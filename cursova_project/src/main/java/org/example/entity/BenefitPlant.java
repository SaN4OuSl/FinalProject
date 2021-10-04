package org.example.entity;

import javax.persistence.*;

@Entity
@Table(name = "benefit_plants")
public class BenefitPlant extends BaseEntity {

    @Column(nullable = false, name = "plant_harvest")
    private Long plantHarvest;

    @Column(nullable = false, name = "cost_of_plant")
    private Long costOfPlant;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "plant_id")
    private Plants plant;
}
