package org.example.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "districts")
public class Districts {
    
    @Id
    @NaturalId
    @Column(nullable = false, name = "koatuu")
    private Long KOATUU;
    
    @Column(nullable = false, name = "district_name")
    private String districtName;
    
    @Column(nullable = false, name = "year_of_statistic")
    private Long yearOfStatistic;
    
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Regions region;
    
    @OneToMany(mappedBy = "district", fetch = FetchType.LAZY)
    private List<Fields> fields;
}
