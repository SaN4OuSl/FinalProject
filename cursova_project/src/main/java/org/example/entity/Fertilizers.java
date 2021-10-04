package org.example.entity;

import javax.persistence.*;
import java.util.List;

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
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Plants> plant;
}
