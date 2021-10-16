package org.example.dto.request.tech;

public class TechniquesDtoRequest {

    private String typeOfTechnique;
    private Integer numberOfTechnique;

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
}
