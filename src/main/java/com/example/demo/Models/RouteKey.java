package com.example.demo.Models;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RouteKey implements Serializable {

    @Column( columnDefinition = "uuid", updatable = false )
    public UUID DestinationID;
    public String City;


    public RouteKey() {
    }

    public RouteKey(UUID DestinationID, String City) {
        this.DestinationID = DestinationID;
        this.City = City;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RouteKey)) {
            return false;
        }
        RouteKey routeKey = (RouteKey) o;
        return Objects.equals(DestinationID, routeKey.DestinationID) && Objects.equals(City, routeKey.City);
    }

    @Override
    public int hashCode() {
        return Objects.hash(DestinationID, City);
    } 
}
