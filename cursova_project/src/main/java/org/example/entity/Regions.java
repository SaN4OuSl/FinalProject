package org.example.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "regions")
public class Regions extends BaseEntity{
    
    @NaturalId
    @Column(nullable = false, name = "region_name")
    private String regionName;
    
    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private List<Districts> districts;
}
