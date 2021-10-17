package org.example.dto.response.tech;


import org.example.entity.tech.SparePart;
import org.example.entity.tech.Technique;

import java.util.List;

public class SparePartsDtoResponse {

    private String nameOfPart;
    private String numberOfParts;
    private Double priceOfPart;
    private List<Long> techniquesIds;

    public SparePartsDtoResponse() {
    }

    public SparePartsDtoResponse(SparePart spareParts) {
        this.nameOfPart = spareParts.getNameOfPart();
        this.numberOfParts = spareParts.getNumberOfParts();
        this.priceOfPart = spareParts.getPriceOfPart();

        for (Technique technique : spareParts.getTechniques()) {
            this.techniquesIds.add(technique.getId());
        }
    }

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

    public List<Long> getTechniquesIds() {
        return techniquesIds;
    }

    public void setTechniquesIds(List<Long> techniquesIds) {
        this.techniquesIds = techniquesIds;
    }
}
