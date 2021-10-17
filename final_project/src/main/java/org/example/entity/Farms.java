package org.example.entity;

import org.example.entity.animal.Buildings;
import org.example.entity.plant.Fields;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "farms",
        uniqueConstraints = {@UniqueConstraint(columnNames = "farm_name")})
public class Farms extends BaseEntity {
    
    @Column(nullable = false, name = "farm_name")
    private String farmName;
    
    @Column(nullable = false, name = "year_of_statistic")
    private Long yearOfStatistic;
    
    @Column(nullable = false)
    private String address;
    
    @OneToMany(mappedBy = "farm", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Fields> fields;
    
    @OneToMany(mappedBy = "farm", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Buildings> buildings;
    
    public String getFarmName() {
        return farmName;
    }
    
    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }
    
    public Long getYearOfStatistic() {
        return yearOfStatistic;
    }
    
    public void setYearOfStatistic(Long yearOfStatistic) {
        this.yearOfStatistic = yearOfStatistic;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public List<Fields> getFields() {
        return fields;
    }
    
    public void setFields(List<Fields> fields) {
        this.fields = fields;
    }
    
    public List<Buildings> getBuildings() {
        return buildings;
    }
    
    public void setBuildings(List<Buildings> buildings) {
        this.buildings = buildings;
    }
}
