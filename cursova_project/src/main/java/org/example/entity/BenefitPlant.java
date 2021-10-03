package org.example.entity;

import javax.persistence.*;

@Entity
@Table(name = "benefit_plants")
public class BenefitPlant extends BaseEntity {

    @Column(nullable = false, name = "number_of_plants")
    private Long numberOfPlants;

    @Column(nullable = false, name = "cost_of_plants")
    private Long costOfPlant;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id")
    private Plants plant;
}
