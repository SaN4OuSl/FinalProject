package org.example.entity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "farms")
public class Farm {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(min = 2, message = "Farm name must be more than 2 characters")
    @Column(nullable = false, name = "farm_name")
    private String farmName;
    
    @Pattern(regexp = "^\\d+$", message = "Year must only contain numbers")
    @Column(nullable = false, name = "year_of_statistic")
    private String yearOfStatistic;
    
    @Size(min = 2, message = "Address must be more than 2 characters")
    @Column(nullable = false)
    private String address;
    
    @OneToMany(mappedBy = "farm", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Plant> plants;
    
    @OneToMany(mappedBy = "farm", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Animal> animals;
    
    @OneToMany(mappedBy = "farm", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Technique> techniques;
    
    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    
    public Farm() {
    }
    
    public Farm(String farmName, String yearOfStatistic, String address) {
        this.farmName = farmName;
        this.yearOfStatistic = yearOfStatistic;
        this.address = address;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFarmName() {
        return farmName;
    }
    
    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }
    
    public String getYearOfStatistic() {
        return yearOfStatistic;
    }
    
    public void setYearOfStatistic(String yearOfStatistic) {
        this.yearOfStatistic = yearOfStatistic;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public List<Plant> getPlants() {
        return plants;
    }
    
    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public List<Animal> getAnimals() {
        return animals;
    }
    
    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }
    
    public List<Technique> getTechniques() {
        return techniques;
    }
    
    public void setTechniques(List<Technique> techniques) {
        this.techniques = techniques;
    }
}
