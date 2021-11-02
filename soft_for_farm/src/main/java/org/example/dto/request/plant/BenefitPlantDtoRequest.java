package org.example.dto.request.plant;

public class BenefitPlantDtoRequest {

    private Long plantHarvest;
    private Long costOfPlant;

    public Long getPlantHarvest() {
        return plantHarvest;
    }

    public void setPlantHarvest(Long plantHarvest) {
        this.plantHarvest = plantHarvest;
    }

    public Long getCostOfPlant() {
        return costOfPlant;
    }

    public void setCostOfPlant(Long costOfPlant) {
        this.costOfPlant = costOfPlant;
    }
}
