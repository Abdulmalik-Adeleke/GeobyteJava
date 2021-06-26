package com.example.demo.Models;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.vividsolutions.jts.geom.Point;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name="selectedroute")
@Immutable
public class Selectedroute implements Serializable {
    @EmbeddedId
    private RouteKey routeKey;
    private int stop_number;
    private Point location_coordinates;
    private int fee;
    private String address;

    public Selectedroute() {
    }

    public RouteKey getRouteKey() {
        return this.routeKey;
    }

    public int getStop_number() {
        return this.stop_number;
    }

    public Point getLocation_coordinates() {
        return this.location_coordinates;
    }

    public int getFee() {
        return this.fee;
    }

    public String getAddress(){
        return this.address;
    }
}
