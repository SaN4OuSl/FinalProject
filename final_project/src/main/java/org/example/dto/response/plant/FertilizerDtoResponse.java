package org.example.dto.response.plant;


import org.example.entity.plant.Fertilizer;
import org.example.entity.plant.Plant;

import java.util.List;

public class FertilizerDtoResponse {

    private String nameOfFertilizers;
    private String nameOfMeasure;
    private Long numberOfFertilizers;
    private Long costOfFertilizers;
    private List<Long> plantsIds;

    public FertilizerDtoResponse() {
    }

    public FertilizerDtoResponse(Fertilizer fertilizer) {
        this.nameOfFertilizers = fertilizer.getNameOfFertilizers();
        this.nameOfMeasure = fertilizer.getNameOfMeasure();
        this.numberOfFertilizers = fertilizer.getNumberOfFertilizers();
        this.costOfFertilizers = fertilizer.getCostOfFertilizers();

        for (Plant plant : fertilizer.getPlant()) {
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
