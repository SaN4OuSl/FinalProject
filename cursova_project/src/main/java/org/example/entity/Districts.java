package org.example.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "districts")
public class Districts extends BaseEntity {
    
    @NaturalId
    @Column(nullable = false, name = "district_name")
    private String districtName;
    
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Regions region;
    
    @OneToMany(mappedBy = "district", fetch = FetchType.LAZY)
    private List<Farms> farms;
}
