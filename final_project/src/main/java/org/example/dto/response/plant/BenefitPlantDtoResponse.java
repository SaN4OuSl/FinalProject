package org.example.dto.response.plant;


import org.example.entity.plant.BenefitPlant;

public class BenefitPlantDtoResponse {

    private Long id;
    private Long plantHarvest;
    private Long costOfPlant;
    private Long plantId;

    public BenefitPlantDtoResponse() {
    }

    public BenefitPlantDtoResponse(BenefitPlant benefitPlant) {
        this.id = benefitPlant.getId();
        this.plantHarvest = benefitPlant.getPlantHarvest();
        this.costOfPlant = benefitPlant.getCostOfPlant();
        this.plantId = benefitPlant.getPlant().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }
}
