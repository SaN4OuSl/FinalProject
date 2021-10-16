package org.example.dto.response.plant;

import org.example.entity.plant.Fields;
import org.example.entity.plant.Plants;

import java.util.List;

public class FieldDtoResponse {

    private String cadastralNumber;
    private Double sizeOfField;
    private Double rentalPrice;
    private Long farmId;
    private List<Long> plantsIds;

    public FieldDtoResponse() {
    }

    public FieldDtoResponse(Fields fields){
        this.cadastralNumber = fields.getCadastralNumber();
        this.sizeOfField = fields.getSizeOfField();
        this.rentalPrice = fields.getRentalPrice();
        this.farmId = fields.getFarm().getId();

        for (Plants plants : fields.getPlants()) {
            this.plantsIds.add(plants.getId());
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
