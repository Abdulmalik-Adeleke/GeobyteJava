package com.example.demo.Dto;

import java.util.Objects;
import java.util.UUID;

public class RouteDto {

    private UUID DestinationId;
    private String City;
    private int StopNumber;


    public RouteDto() {
    }

    public RouteDto(UUID DestinationId, String City, int StopNumber) {
        this.DestinationId = DestinationId;
        this.City = City;
        this.StopNumber = StopNumber;
    }

    public UUID getDestinationId() {
        return this.DestinationId;
    }

    public void setDestinationId(UUID DestinationId) {
        this.DestinationId = DestinationId;
    }

    public String getCity() {
        return this.City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public int getStopNumber() {
        return this.StopNumber;
    }

    public void setStopNumber(int StopNumber) {
        this.StopNumber = StopNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RouteDto)) {
            return false;
        }
        RouteDto routeDto = (RouteDto) o;
        return Objects.equals(DestinationId, routeDto.DestinationId) && Objects.equals(City, routeDto.City) && StopNumber == routeDto.StopNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(DestinationId, City, StopNumber);
    }

    @Override
    public String toString() {
        return "{" +
            " DestinationId='" + getDestinationId() + "'" +
            ", City='" + getCity() + "'" +
            ", StopNumber='" + getStopNumber() + "'" +
            "}";
    }



}
