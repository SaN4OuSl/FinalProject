package org.example.dto.request.animal;

public class AnimalDtoRequest {

    private String animalName;
    private Integer numberOfAnimals;
    private Long proceeds;

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public Integer getNumberOfAnimals() {
        return numberOfAnimals;
    }

    public void setNumberOfAnimals(Integer numberOfAnimals) {
        this.numberOfAnimals = numberOfAnimals;
    }

    public Long getProceeds() {
        return proceeds;
    }

    public void setProceeds(Long proceeds) {
        this.proceeds = proceeds;
    }
}
