package org.example.dto.response.animal;


import org.example.entity.animal.Animals;
import org.example.entity.animal.Buildings;

import java.util.List;

public class BuildingDtoResponse {

    private Double sizeOfBuilding;
    private Double rentalPrice;
    private Long farmId;
    private List<Long> animalsId;

    public BuildingDtoResponse(){}

    public BuildingDtoResponse(Buildings buildings){
        this.sizeOfBuilding = buildings.getSizeOfBuilding();
        this.rentalPrice = buildings.getRentalPrice();
        this.farmId = buildings.getFarm().getId();

        for (Animals animal : buildings.getAnimal()) {
            this.animalsId.add(animal.getId());
        }
    }

    public Double getSizeOfBuilding() {
        return sizeOfBuilding;
    }

    public void setSizeOfBuilding(Double sizeOfBuilding) {
        this.sizeOfBuilding = sizeOfBuilding;
    }

    public Double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(Double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public Long getFarmId() {
        return farmId;
    }

    public void setFarmId(Long farmId) {
        this.farmId = farmId;
    }

    public List<Long> getAnimalsId() {
        return animalsId;
    }

    public void setAnimalsId(List<Long> animalsId) {
        this.animalsId = animalsId;
    }
}
