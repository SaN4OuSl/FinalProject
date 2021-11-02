package org.example.dto.request.animal;

public class FeedDtoRequest {

    private String nameOfFeed;
    private String nameOfMeasure;
    private Long numberOfFeed;
    private Double costOfFeed;

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
}
