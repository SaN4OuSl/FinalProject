package org.example.dto.response.animal;

import org.example.entity.animal.Animal;
import org.example.entity.animal.Building;
import org.example.entity.animal.Feed;
import org.example.entity.tech.Technique;

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

    public AnimalDtoResponse(Animal animal) {
        this.animalName = animal.getAnimalName();
        this.numberOfAnimals = animal.getNumberOfAnimals();
        this.proceeds = animal.getProceeds();

        for (Feed feed : animal.getFeeds()){
            this.feedsIds.add(feed.getId());
        }

        for (Technique technique : animal.getTechniques()){
            this.techniquesIds.add(technique.getId());
        }

        for (Building building : animal.getBuildings()) {
            this.buildingsIds.add(building.getId());
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
