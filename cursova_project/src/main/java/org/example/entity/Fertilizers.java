package org.example.entity;

import javax.persistence.*;

@Entity
@Table(name = "fertilizers")
public class Fertilizers extends BaseEntity {

    @Column(nullable = false, name = "name_of_fertilizer")
    private String nameOfFertilizers;
    
    @Column(nullable = false, name = "name_of_measure")
    private String nameOfMeasure;

    @Column(nullable = false,  name = "number_of_fertilizer")
    private Long numberOfFertilizers;

    @Column(nullable = false, name = "cost_of_fertilizer")
    private Long costOfFertilizers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id")
    private Plants plant;
}
