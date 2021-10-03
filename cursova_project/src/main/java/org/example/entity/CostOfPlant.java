package org.example.entity;

import javax.persistence.*;

@Entity
@Table(name = "cost_plants")
public class CostOfPlant extends BaseEntity {

    @Column(nullable = false)
    private String nameOfResource;

    @Column(nullable = false)
    private Long numberOfResource;

    @Column(nullable = false)
    private Long costOfResource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id")
    private Plants plant;
}
