package org.example.entity.animal;

import org.example.entity.*;
import org.example.entity.tech.Techniques;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "animals")
public class Animals extends BaseEntity {

    @Column(name = "animal_name")
    private String animalName;

    @Column(name = "number_of_animals")
    private Integer numberOfAnimals;

    private Long proceeds;

    @ManyToMany(mappedBy = "animal", fetch = FetchType.LAZY)
    private List<Feeds> feeds;

    @ManyToMany(mappedBy = "animal", fetch = FetchType.LAZY)
    private List<Techniques> techniques;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Buildings> buildings;

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

    public List<Feeds> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<Feeds> feeds) {
        this.feeds = feeds;
    }

    public List<Techniques> getTechniques() {
        return techniques;
    }

    public void setTechniques(List<Techniques> techniques) {
        this.techniques = techniques;
    }

    public List<Buildings> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Buildings> buildings) {
        this.buildings = buildings;
    }
}
