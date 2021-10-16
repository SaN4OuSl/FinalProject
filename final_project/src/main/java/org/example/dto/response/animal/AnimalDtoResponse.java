package org.example.dto.response.animal;

import org.example.entity.animal.Animals;
import org.example.entity.animal.Buildings;
import org.example.entity.animal.Feeds;
import org.example.entity.tech.Techniques;

import java.util.List;

public class AnimalDtoResponse {

    private String animalName;
    private Integer numberOfAnimals;
    private Long proceeds;
    private List<Long> feedsIds;
    private List<Long> techniquesIds;
    private List<Long> buildingsIds;

    public AnimalDtoResponse() {
    }

    public AnimalDtoResponse(Animals animals) {
        this.animalName = animals.getAnimalName();
        this.numberOfAnimals = animals.getNumberOfAnimals();
        this.proceeds = animals.getProceeds();

        for (Feeds feed : animals.getFeeds()){
            this.feedsIds.add(feed.getId());
        }

        for (Techniques techniques : animals.getTechniques()){
            this.techniquesIds.add(techniques.getId());
        }

        for (Buildings buildings : animals.getBuildings()) {
            this.buildingsIds.add(buildings.getId());
        }
    }

    public String getAnimalName() {
        return animalName;
    }

    public Integer getNumberOfAnimals() {
        return numberOfAnimals;
    }

    public Long getProceeds() {
        return proceeds;
    }

    public List<Long> getFeedsIds() {
        return feedsIds;
    }

    public List<Long> getTechniquesIds() {
        return techniquesIds;
    }

    public List<Long> getBuildingsIds() {
        return buildingsIds;
    }
}
