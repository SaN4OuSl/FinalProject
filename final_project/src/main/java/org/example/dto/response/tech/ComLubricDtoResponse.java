package org.example.dto.response.tech;

import org.example.entity.tech.CombustibleLubricants;
import org.example.entity.tech.Techniques;

import java.util.List;

public class ComLubricDtoResponse {

    private String nameOfLubricant;
    private Double numberOfLubricant;
    private Double priceOfLubricant;
    private List<Long> techniquesIds;

    public ComLubricDtoResponse() {
    }

    public ComLubricDtoResponse(CombustibleLubricants combustibleLubricants) {

        this.nameOfLubricant = combustibleLubricants.getNameOfLubricant();
        this.numberOfLubricant = combustibleLubricants.getNumberOfLubricant();
        this.priceOfLubricant = combustibleLubricants.getPriceOfLubricant();

        for (Techniques techniques : combustibleLubricants.getTechniques()) {
            this.techniquesIds.add(techniques.getId());
        }
    }

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

    public List<Long> getTechniquesIds() {
        return techniquesIds;
    }

    public void setTechniquesIds(List<Long> techniquesIds) {
        this.techniquesIds = techniquesIds;
    }
}
