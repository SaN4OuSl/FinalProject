package org.example.dto.request.plant;

public class FieldDtoRequest {

    private String сadastralNumber;
    private Double sizeOfField;
    private Double rentalPrice;

    public String getСadastralNumber() {
        return сadastralNumber;
    }

    public void setСadastralNumber(String сadastralNumber) {
        this.сadastralNumber = сadastralNumber;
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
}
