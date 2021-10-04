package org.example.entity;


import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "fields")
public class Fields {
    
    @Id
    @NaturalId
    @Column(nullable = false, name = "сadastral_number")
    private String сadastralNumber;
    
    @Column(nullable = false, name = "size_of_field")
    private Long sizeOfField;
    
    @Column(name = "rental_price")
    private Long rentalPrice;
    
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "farm_id")
    private Farms farm;
    
    @ManyToMany(mappedBy = "field", fetch = FetchType.LAZY)
    private List<Plants> plants;
}
