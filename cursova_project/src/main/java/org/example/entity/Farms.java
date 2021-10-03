package org.example.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "farms")
public class Farms extends BaseEntity {
    
    @NaturalId
    @Column(nullable = false, name = "farm_name")
    private String farmName;
    
    @Column(nullable = false)
    private Long year;
    
    @OneToMany(mappedBy = "farm", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Plants> plants;
    
    @ManyToOne
    @JoinColumn(name = "district_id")
    private Districts district;
    
    public Farms() {
        plants = new ArrayList<>();
    }
    
    public String getName() {
        return farmName;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Farms)) return false;
        Farms course = (Farms) o;
        return getName().equals(course.getName());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
