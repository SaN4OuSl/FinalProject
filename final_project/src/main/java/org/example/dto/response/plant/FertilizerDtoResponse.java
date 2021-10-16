package org.example.dto.response.plant;


import org.example.entity.plant.Fertilizers;
import org.example.entity.plant.Plants;

import java.util.List;

public class FertilizerDtoResponse {

    private String nameOfFertilizers;
    private String nameOfMeasure;
    private Long numberOfFertilizers;
    private Long costOfFertilizers;
    private List<Long> plantsIds;

    public FertilizerDtoResponse() {
    }

    public FertilizerDtoResponse(Fertilizers fertilizers) {
        this.nameOfFertilizers = fertilizers.getNameOfFertilizers();
        this.nameOfMeasure = fertilizers.getNameOfMeasure();
        this.numberOfFertilizers = fertilizers.getNumberOfFertilizers();
        this.costOfFertilizers = fertilizers.getCostOfFertilizers();

        for (Plants plant : fertilizers.getPlant()) {
            this.plantsIds.add(plant.getId());
        }
    }

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

    public List<Long> getPlantsIds() {
        return plantsIds;
    }

    public void setPlantsIds(List<Long> plantsIds) {
        this.plantsIds = plantsIds;
    }
}
