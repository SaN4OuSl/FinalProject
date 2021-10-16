package org.example.dto.response.tech;

import org.example.entity.animal.Animals;
import org.example.entity.plant.Plants;
import org.example.entity.tech.CombustibleLubricants;
import org.example.entity.tech.SpareParts;
import org.example.entity.tech.Techniques;

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

    public TechniquesDtoResponse(Techniques techniques) {
        this.typeOfTechnique = techniques.getTypeOfTechnique();
        this.numberOfTechnique = techniques.getNumberOfTechnique();

        for (Plants plants : techniques.getPlant()) {
            this.plantsIds.add(plants.getId());
        }

        for (Animals animal : techniques.getAnimal()) {
            this.animalsIds.add(animal.getId());
        }

        for (SpareParts sparePart : techniques.getSpareParts()) {
            this.sparePartsIds.add(sparePart.getId());
        }

        for (CombustibleLubricants combustibleLubricants : techniques.getCombustibleLubricants()) {
            this.combustibleLubricantsIds.add(combustibleLubricants.getId());
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
