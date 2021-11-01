package org.example.entity.animal;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "feeds")
public class Feed {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_of_feed")
    private String nameOfFeed;

    @Column(name = "name_of_measure")
    private String nameOfMeasure;

    @Column(name = "number_of_feed")
    private Long numberOfFeed;

    @Column(name = "cost_of_feed")
    private Double costOfFeed;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Animal> animal;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNameOfFeed() {
        return nameOfFeed;
    }

    public void setNameOfFeed(String nameOfFeed) {
        this.nameOfFeed = nameOfFeed;
    }

    public String getNameOfMeasure() {
        return nameOfMeasure;
    }

    public void setNameOfMeasure(String nameOfMeasure) {
        this.nameOfMeasure = nameOfMeasure;
    }

    public Long getNumberOfFeed() {
        return numberOfFeed;
    }

    public void setNumberOfFeed(Long numberOfFeed) {
        this.numberOfFeed = numberOfFeed;
    }

    public Double getCostOfFeed() {
        return costOfFeed;
    }

    public void setCostOfFeed(Double costOfFeed) {
        this.costOfFeed = costOfFeed;
    }

    public List<Animal> getAnimal() {
        return animal;
    }

    public void setAnimal(List<Animal> animal) {
        this.animal = animal;
    }
}
