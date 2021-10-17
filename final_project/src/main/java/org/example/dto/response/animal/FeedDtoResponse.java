package org.example.dto.response.animal;

import org.example.entity.animal.Animal;
import org.example.entity.animal.Feed;

import java.util.List;

public class FeedDtoResponse {

    private String nameOfFeed;
    private String nameOfMeasure;
    private Long numberOfFeed;
    private Double costOfFeed;
    private List<Long> animalsId;

    public FeedDtoResponse() {
    }

    public FeedDtoResponse(Feed feed) {
        this.nameOfFeed = feed.getNameOfFeed();
        this.nameOfMeasure = feed.getNameOfMeasure();
        this.nameOfFeed = feed.getNameOfFeed();
        this.numberOfFeed = feed.getNumberOfFeed();
        this.costOfFeed = feed.getCostOfFeed();

        for (Animal animal : feed.getAnimal()) {
            this.animalsId.add(animal.getId());
        }
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

    public List<Long> getAnimalsId() {
        return animalsId;
    }

    public void setAnimalsId(List<Long> animalsId) {
        this.animalsId = animalsId;
    }
}
