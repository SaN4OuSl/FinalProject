package org.example.entity;

import org.example.entity.animal.Building;
import org.example.entity.plant.Field;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "farms",
        uniqueConstraints = {@UniqueConstraint(columnNames = "farm_name")})
public class Farm extends BaseEntity {
    
    @Column(nullable = false, name = "farm_name")
    private String farmName;
    
    @Column(nullable = false, name = "year_of_statistic")
    private Long yearOfStatistic;
    
    @Column(nullable = false)
    private String address;
    
    @OneToMany(mappedBy = "farm", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Field> fields;
    
    @OneToMany(mappedBy = "farm", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Building> buildings;
    
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(nullable = false,name = "user_id")
    private User user;
    
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
    
    public List<Field> getFields() {
        return fields;
    }
    
    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
    
    public List<Building> getBuildings() {
        return buildings;
    }
    
    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }
}
