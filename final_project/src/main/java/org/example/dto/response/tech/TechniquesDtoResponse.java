package org.example.dto.response.tech;

import org.example.entity.animal.Animal;
import org.example.entity.plant.Plant;
import org.example.entity.tech.CombustibleLubricant;
import org.example.entity.tech.SparePart;
import org.example.entity.tech.Technique;

import java.util.List;

public class TechniquesDtoResponse {

    private String typeOfTechnique;
    private Integer numberOfTechnique;
    private List<Long> plantsIds;
    private List<Long> animalsIds;
    private List<Long> sparePartsIds;
    private List<Long> combustibleLubricantsIds;

    public TechniquesDtoResponse() {
    }

    public TechniquesDtoResponse(Technique technique) {
        this.typeOfTechnique = technique.getTypeOfTechnique();
        this.numberOfTechnique = technique.getNumberOfTechnique();

        for (Plant plant : technique.getPlant()) {
            this.plantsIds.add(plant.getId());
        }

        for (Animal animal : technique.getAnimal()) {
            this.animalsIds.add(animal.getId());
        }

        for (SparePart sparePart : technique.getSpareParts()) {
            this.sparePartsIds.add(sparePart.getId());
        }

        for (CombustibleLubricant combustibleLubricant : technique.getCombustibleLubricants()) {
            this.combustibleLubricantsIds.add(combustibleLubricant.getId());
        }
    }

    public String getTypeOfTechnique() {
        return typeOfTechnique;
    }

    public void setTypeOfTechnique(String typeOfTechnique) {
        this.typeOfTechnique = typeOfTechnique;
    }

    public Integer getNumberOfTechnique() {
        return numberOfTechnique;
    }

    public void setNumberOfTechnique(Integer numberOfTechnique) {
        this.numberOfTechnique = numberOfTechnique;
    }

    public List<Long> getPlantsIds() {
        return plantsIds;
    }

    public void setPlantsIds(List<Long> plantsIds) {
        this.plantsIds = plantsIds;
    }

    public List<Long> getAnimalsIds() {
        return animalsIds;
    }

    public void setAnimalsIds(List<Long> animalsIds) {
        this.animalsIds = animalsIds;
    }

    public List<Long> getSparePartsIds() {
        return sparePartsIds;
    }

    public void setSparePartsIds(List<Long> sparePartsIds) {
        this.sparePartsIds = sparePartsIds;
    }

    public List<Long> getCombustibleLubricantsIds() {
        return combustibleLubricantsIds;
    }

    public void setCombustibleLubricantsIds(List<Long> combustibleLubricantsIds) {
        this.combustibleLubricantsIds = combustibleLubricantsIds;
    }
}
