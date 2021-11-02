package org.example.dto.request.tech;

public class SparePartsDtoRequest {

    private String nameOfPart;
    private String numberOfParts;
    private Double priceOfPart;

    public String getNameOfPart() {
        return nameOfPart;
    }

    public void setNameOfPart(String nameOfPart) {
        this.nameOfPart = nameOfPart;
    }

    public String getNumberOfParts() {
        return numberOfParts;
    }

    public void setNumberOfParts(String numberOfParts) {
        this.numberOfParts = numberOfParts;
    }

    public Double getPriceOfPart() {
        return priceOfPart;
    }

    public void setPriceOfPart(Double priceOfPart) {
        this.priceOfPart = priceOfPart;
    }
}
