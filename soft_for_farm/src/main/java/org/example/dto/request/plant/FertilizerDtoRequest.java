package org.example.dto.request.plant;

public class FertilizerDtoRequest {

    private String nameOfFertilizers;
    private String nameOfMeasure;
    private Long numberOfFertilizers;
    private Long costOfFertilizers;

    public String getNameOfFertilizers() {
        return nameOfFertilizers;
    }

    public void setNameOfFertilizers(String nameOfFertilizers) {
        this.nameOfFertilizers = nameOfFertilizers;
    }

    public String getNameOfMeasure() {
        return nameOfMeasure;
    }

    public void setNameOfMeasure(String nameOfMeasure) {
        this.nameOfMeasure = nameOfMeasure;
    }

    public Long getNumberOfFertilizers() {
        return numberOfFertilizers;
    }

    public void setNumberOfFertilizers(Long numberOfFertilizers) {
        this.numberOfFertilizers = numberOfFertilizers;
    }

    public Long getCostOfFertilizers() {
        return costOfFertilizers;
    }

    public void setCostOfFertilizers(Long costOfFertilizers) {
        this.costOfFertilizers = costOfFertilizers;
    }
}
