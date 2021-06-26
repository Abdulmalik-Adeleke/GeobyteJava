package com.example.demo.Dto;

import java.util.Objects;

public class DestinationDto {
    private String StartCity;
    private String EndCity;
    private int RouteDistance;

    public DestinationDto() {
    }

    public DestinationDto(String StartCity, String EndCity, int RouteDistance) {
        this.StartCity = StartCity;
        this.EndCity = EndCity;
        this.RouteDistance = RouteDistance;
    }

    public String getStartCity() {
        return this.StartCity;
    }

    public void setStartCity(String StartCity) {
        this.StartCity = StartCity;
    }

    public String getEndCity() {
        return this.EndCity;
    }

    public void setEndCity(String EndCity) {
        this.EndCity = EndCity;
    }

    public int getRouteDistance() {
        return this.RouteDistance;
    }

    public void setRouteDistance(int RouteDistance) {
        this.RouteDistance = RouteDistance;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DestinationDto)) {
            return false;
        }
        DestinationDto destinationDto = (DestinationDto) o;
        return Objects.equals(StartCity, destinationDto.StartCity) && Objects.equals(EndCity, destinationDto.EndCity) && RouteDistance == destinationDto.RouteDistance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(StartCity, EndCity, RouteDistance);
    }

    @Override
    public String toString() {
        return "{" +
            " StartCity='" + getStartCity() + "'" +
            ", EndCity='" + getEndCity() + "'" +
            ", RouteDistance='" + getRouteDistance() + "'" +
            "}";
    }

}
