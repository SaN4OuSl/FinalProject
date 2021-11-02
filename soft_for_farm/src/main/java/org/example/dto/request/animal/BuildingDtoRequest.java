package org.example.dto.request.animal;

public class BuildingDtoRequest {

    private Double sizeOfBuilding;
    private Double rentalPrice;

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
}
