package org.example.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "farms")
public class Farms extends BaseEntity {
    
    @Column(nullable = false, name = "farm_name")
    private String farmName;
    
    @Column(nullable = false, name = "year_of_statistic")
    private Long yearOfStatistic;
    
    @Column(nullable = false)
    private String address;
    
    @OneToMany(mappedBy = "farm", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Fields> fields;
    
}
