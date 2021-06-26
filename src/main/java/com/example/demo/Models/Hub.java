package com.example.demo.Models;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.vividsolutions.jts.geom.Point;


@Entity
@Table
public class Hub {
    
    // public String Cityalias;
    // public String CityID;
    @Id
    private String City;
    private String Address;
    private Point LocationCoordinates;
    private int Fee;
    

    public Hub() {
    }

    public Hub(String City, String Address, Point LocationCoordinates, int Fee) {
        this.City = City;
        this.Address = Address;
        this.LocationCoordinates = LocationCoordinates;
        this.Fee = Fee;
    }

    public String getCity() {
        return this.City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getAddress() {
        return this.Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public Point getLocationCoordinates() {
        return this.LocationCoordinates;
    }

    public void setLocationCoordinates(Point LocationCoordinates) {
        this.LocationCoordinates = LocationCoordinates;
    }

    public int getFee() {
        return this.Fee;
    }

    public void setFee(int Fee) {
        this.Fee = Fee;
    }



    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Hub)) {
            return false;
        }
        Hub hub = (Hub) o;
        return Objects.equals(City, hub.City) && Objects.equals(Address, hub.Address) && Objects.equals(LocationCoordinates, hub.LocationCoordinates) && Fee == hub.Fee;
    }

    @Override
    public int hashCode() {
        return Objects.hash(City, Address, LocationCoordinates, Fee);
    }

    @Override
    public String toString() {
        return "{" +
            " CityName='" + getCity() + "'" +
            ", Address='" + getAddress() + "'" +
            ", LocationCoordinates='" + getLocationCoordinates() + "'" +
            ", Fee='" + getFee() + "'" +
            "}";
    }

 
}

