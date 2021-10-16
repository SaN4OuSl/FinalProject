package org.example.dto.response.plant;

import org.example.entity.plant.Fertilizers;
import org.example.entity.plant.Fields;
import org.example.entity.plant.Plants;
import org.example.entity.tech.Techniques;

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

    public PlantDtoResponse(Plants plants) {
        this.id = plants.getId();
        this.plantName = plants.getPlantName();
        this.sizeOfFieldForPlant = plants.getSizeOfFieldForPlant();
        this.benefitPlantsId = plants.getBenefitPlants().getId();

        for (Fertilizers fertilizers : plants.getFertilizers()) {
            this.fertilizersIds.add(fertilizers.getId());
        }
        for (Techniques techniques : plants.getTechniques()){
            this.techniquesIds.add(techniques.getId());
        }
        for (Fields fields : plants.getField()){
            this.fieldIds.add(fields.getCadastralNumber());
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
