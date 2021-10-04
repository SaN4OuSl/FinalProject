package org.example.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "regions")
public class Regions {
    
    @Id
    @NaturalId
    @Column(nullable = false, name = "koatuu")
    private Long KOATUU;
    
    @NaturalId
    @Column(nullable = false, name = "region_name")
    private String regionName;
    
    @Column(nullable = false, name = "year_of_statistic")
    private Long yearOfStatistic;
    
    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private List<Districts> districts;
}
