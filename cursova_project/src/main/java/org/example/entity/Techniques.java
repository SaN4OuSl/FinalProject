package org.example.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "techniques")
public class Techniques extends BaseEntity{
    
    @Column(nullable = false, name = "type_of_technique")
    private String typeOfTechnique;
    
    @Column(nullable = false, name = "number_of_technique")
    private Integer numberOfTechnique;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Plants> plant;
    
    @ManyToMany(mappedBy = "techniques", fetch = FetchType.LAZY)
    private List<SpareParts> spareParts;
    
    @ManyToMany(mappedBy = "techniques", fetch = FetchType.LAZY)
    private List<CombustibleLubricants> combustibleLubricants;
}
