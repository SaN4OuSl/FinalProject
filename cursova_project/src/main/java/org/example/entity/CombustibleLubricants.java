package org.example.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "combustible_lubricants")
public class CombustibleLubricants extends BaseEntity{
    
    @Column(nullable = false, name = "name_of_lubricant")
    private String nameOfLubricant;
    
    @Column(nullable = false, name = "number_of_lubricant")
    private Double numberOfLubricant;
    
    @Column(nullable = false, name = "price_of_lubricant")
    private Long priceOfLubricant;
    
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Techniques> techniques;
}
