package org.example.entity.animal;

import org.example.entity.tech.Technique;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "animals")
public class Animal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "animal_name")
    private String animalName;

    @Column(name = "number_of_animals")
    private Integer numberOfAnimals;

    private Long proceeds;

    @ManyToMany(mappedBy = "animal", fetch = FetchType.LAZY)
    private List<Feed> feeds;

    @ManyToMany(mappedBy = "animal", fetch = FetchType.LAZY)
    private List<Technique> techniques;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Building> buildings;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public Integer getNumberOfAnimals() {
        return numberOfAnimals;
    }

    public void setNumberOfAnimals(Integer numberOfAnimals) {
        this.numberOfAnimals = numberOfAnimals;
    }

    public Long getProceeds() {
        return proceeds;
    }

    public void setProceeds(Long proceeds) {
        this.proceeds = proceeds;
    }

    public List<Feed> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<Feed> feeds) {
        this.feeds = feeds;
    }

    public List<Technique> getTechniques() {
        return techniques;
    }

    public void setTechniques(List<Technique> techniques) {
        this.techniques = techniques;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }
}
