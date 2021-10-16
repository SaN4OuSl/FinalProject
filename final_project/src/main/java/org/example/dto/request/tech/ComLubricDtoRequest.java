package org.example.dto.request.tech;

public class ComLubricDtoRequest {

    private String nameOfLubricant;
    private Double numberOfLubricant;
    private Double priceOfLubricant;

    public String getNameOfLubricant() {
        return nameOfLubricant;
    }

    public void setNameOfLubricant(String nameOfLubricant) {
        this.nameOfLubricant = nameOfLubricant;
    }

    public Double getNumberOfLubricant() {
        return numberOfLubricant;
    }

    public void setNumberOfLubricant(Double numberOfLubricant) {
        this.numberOfLubricant = numberOfLubricant;
    }

    public Double getPriceOfLubricant() {
        return priceOfLubricant;
    }

    public void setPriceOfLubricant(Double priceOfLubricant) {
        this.priceOfLubricant = priceOfLubricant;
    }
}
