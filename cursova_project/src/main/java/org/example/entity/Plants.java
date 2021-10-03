package org.example.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plants")
public class Plants extends BaseEntity{

    @Column(nullable = false)
    private String plantName;

    @Column(nullable = false)
    private Integer sizeOfField;

    @OneToMany(mappedBy = "plant", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<BenefitPlant> benefitPlants;

    @OneToMany(mappedBy = "plant", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<CostOfPlant> costOfPlants;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "farm_id")
    private Farms farm;

    public Farms getFarm() {
        return farm;
    }

    public void setFarm(Farms farm) {
        this.farm = farm;
    }

    public Plants() {
        benefitPlants = new ArrayList<>();
        costOfPlants = new ArrayList<>();
    }
}
