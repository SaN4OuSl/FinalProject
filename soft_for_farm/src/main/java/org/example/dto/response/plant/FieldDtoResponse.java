package org.example.dto.response.plant;

import org.example.entity.plant.Field;
import org.example.entity.plant.Plant;

import java.util.List;

public class FieldDtoResponse {

    private String cadastralNumber;
    private Double sizeOfField;
    private Double rentalPrice;
    private Long farmId;
    private List<Long> plantsIds;

    public FieldDtoResponse() {
    }

    public FieldDtoResponse(Field field){
        this.cadastralNumber = field.getCadastralNumber();
        this.sizeOfField = field.getSizeOfField();
        this.rentalPrice = field.getRentalPrice();
        this.farmId = field.getFarm().getId();

        for (Plant plant : field.getPlants()) {
            this.plantsIds.add(plant.getId());
        }
    }

    public String getCadastralNumber() {
        return cadastralNumber;
    }

    public void setCadastralNumber(String cadastralNumber) {
        this.cadastralNumber = cadastralNumber;
    }

    public Double getSizeOfField() {
        return sizeOfField;
    }

    public void setSizeOfField(Double sizeOfField) {
        this.sizeOfField = sizeOfField;
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

    public List<Long> getPlantsIds() {
        return plantsIds;
    }

    public void setPlantsIds(List<Long> plantsIds) {
        this.plantsIds = plantsIds;
    }
}
