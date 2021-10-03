package org.example.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "farms")
public class Farms extends BaseEntity {
    
    @NaturalId
    @Column(nullable = false, name = "farm_name")
    private String farmName;
    
    @Column(nullable = false)
    private Long year;
    
    @Column(nullable = false)
    private String address;
    
    @OneToMany(mappedBy = "farm", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Plants> plants;
    
    @ManyToOne
    @JoinColumn(name = "district_id")
    private Districts district;
}
