package org.example.dto.request.plant;

public class PlantsDtoRequest {

    private String plantName;
    private Integer sizeOfFieldForPlant;

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
}
