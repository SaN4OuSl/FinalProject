package org.example.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "benefit_plants")
public class BenefitPlant extends BaseEntity {

    @Column(nullable = false)
    private Long numberPlants;

    @Column(nullable = false)
    private Long costOfPlant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id")
    private Plants plant;
}
