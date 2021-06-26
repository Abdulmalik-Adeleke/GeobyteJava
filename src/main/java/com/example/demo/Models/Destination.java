package com.example.demo.Models;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Destination {
    @Id
    @Column( columnDefinition = "uuid", updatable = false )
    private UUID DestinationID;
    private String StartCity;
    private String EndCity;
    private int RouteDistance;

    public Destination() {
    }

    public Destination(UUID DestinationID, String StartCity, String EndCity, int RouteDistance) {
        this.DestinationID = DestinationID;
        this.StartCity = StartCity;
        this.EndCity = EndCity;
        this.RouteDistance = RouteDistance;
    }

    public UUID getDestinationID() {
        return this.DestinationID;
    }

    public void setDestinationID(UUID DestinationID) {
        this.DestinationID = DestinationID;
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
        if (!(o instanceof Destination)) {
            return false;
        }
        Destination destination = (Destination) o;
        return Objects.equals(DestinationID, destination.DestinationID) && Objects.equals(StartCity, destination.StartCity) && Objects.equals(EndCity, destination.EndCity) && RouteDistance == destination.RouteDistance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(DestinationID, StartCity, EndCity, RouteDistance);
    }
  

    @Override
    public String toString() {
        return "{" +
            " DestinationID='" + getDestinationID() + "'" +
            ", StartCity='" + getStartCity() + "'" +
            ", EndCity='" + getEndCity() + "'" +
            ", RouteDistance='" + getRouteDistance() + "'" +
            "}";
    }
    

}
