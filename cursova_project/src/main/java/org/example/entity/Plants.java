package org.example.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "plants")
public class Plants extends BaseEntity{

    @Column(nullable = false, name = "plant_name")
    private String plantName;

    @Column(nullable = false, name = "size_of_field")
    private Integer sizeOfField;

    @OneToOne(mappedBy = "plant", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private BenefitPlant benefitPlants;

    @OneToMany(mappedBy = "plant", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Fertilizers> fertilizers;
    
    @ManyToMany(mappedBy = "plant", fetch = FetchType.LAZY)
    private List<Techniques> techniques;
    
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "farm_id")
    private Farms farm;
}
