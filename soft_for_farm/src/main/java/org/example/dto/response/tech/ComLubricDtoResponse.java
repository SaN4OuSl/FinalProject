package org.example.dto.response.tech;

import org.example.entity.tech.CombustibleLubricant;
import org.example.entity.tech.Technique;

import java.util.List;

public class ComLubricDtoResponse {

    private String nameOfLubricant;
    private Double numberOfLubricant;
    private Double priceOfLubricant;
    private List<Long> techniquesIds;

    public ComLubricDtoResponse() {
    }

    public ComLubricDtoResponse(CombustibleLubricant combustibleLubricant) {

        this.nameOfLubricant = combustibleLubricant.getNameOfLubricant();
        this.numberOfLubricant = combustibleLubricant.getNumberOfLubricant();
        this.priceOfLubricant = combustibleLubricant.getPriceOfLubricant();

        for (Technique technique : combustibleLubricant.getTechniques()) {
            this.techniquesIds.add(technique.getId());
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
