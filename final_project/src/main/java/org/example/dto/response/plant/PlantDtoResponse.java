package org.example.dto.response.plant;

import org.example.entity.plant.Fertilizer;
import org.example.entity.plant.Field;
import org.example.entity.plant.Plant;
import org.example.entity.tech.Technique;

import java.util.List;

public class PlantDtoResponse {

    private Long id;
    private String plantName;
    private Integer sizeOfFieldForPlant;
    private Long benefitPlantsId;
    private List<Long> fertilizersIds;
    private List<Long> techniquesIds;
    private List<String> fieldIds;

    public PlantDtoResponse() {
    }

    public PlantDtoResponse(Plant plant) {
        this.id = plant.getId();
        this.plantName = plant.getPlantName();
        this.sizeOfFieldForPlant = plant.getSizeOfFieldForPlant();
        this.benefitPlantsId = plant.getBenefitPlants().getId();

        for (Fertilizer fertilizer : plant.getFertilizers()) {
            this.fertilizersIds.add(fertilizer.getId());
        }
        for (Technique technique : plant.getTechniques()){
            this.techniquesIds.add(technique.getId());
        }
        for (Field field : plant.getField()){
            this.fieldIds.add(field.getCadastralNumber());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public Integer getSizeOfFieldForPlant() {
        return sizeOfFieldForPlant;
    }

    public void setSizeOfFieldForPlant(Integer sizeOfFieldForPlant) {
        this.sizeOfFieldForPlant = sizeOfFieldForPlant;
    }

    public Long getBenefitPlantsId() {
        return benefitPlantsId;
    }

    public void setBenefitPlantsId(Long benefitPlantsId) {
        this.benefitPlantsId = benefitPlantsId;
    }

    public List<Long> getFertilizersIds() {
        return fertilizersIds;
    }

    public void setFertilizersIds(List<Long> fertilizersIds) {
        this.fertilizersIds = fertilizersIds;
    }

    public List<Long> getTechniquesIds() {
        return techniquesIds;
    }

    public void setTechniquesIds(List<Long> techniquesIds) {
        this.techniquesIds = techniquesIds;
    }

    public List<String> getFieldIds() {
        return fieldIds;
    }

    public void setFieldIds(List<String> fieldIds) {
        this.fieldIds = fieldIds;
    }
}
