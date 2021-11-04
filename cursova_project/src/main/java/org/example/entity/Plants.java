package org.example.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "plants")
public class Plants extends BaseEntity{

    @Column(nullable = false, name = "plant_name")
    private String plantName;
    
    @Column(nullable = false, name = "size_of_field_for_plant")
    private Integer sizeOfFieldForPlant;

    @OneToOne(mappedBy = "plant", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private BenefitPlant benefitPlants;

    @ManyToMany(mappedBy = "plant", fetch = FetchType.LAZY)
    private List<Fertilizers> fertilizers;
    
    @ManyToMany(mappedBy = "plant", fetch = FetchType.LAZY)
    private List<Techniques> techniques;
    
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Fields> field;
}
