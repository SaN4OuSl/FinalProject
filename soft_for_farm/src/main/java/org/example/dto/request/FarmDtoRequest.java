package org.example.dto.request;


public class FarmDtoRequest {

    private String farmName;
    private Long yearOfStatistic;
    private String address;

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public Long getYearOfStatistic() {
        return yearOfStatistic;
    }

    public void setYearOfStatistic(Long yearOfStatistic) {
        this.yearOfStatistic = yearOfStatistic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
