package org.example.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "spare_parts")
public class SpareParts extends BaseEntity{
    
    @Column(name = "name_of_part")
    private String nameOfPart;
    
    @Column(name = "number_of_parts")
    private String numberOfParts;
    
    @Column(name = "price_of_part")
    private Long priceOfPart;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Techniques> techniques;
}
