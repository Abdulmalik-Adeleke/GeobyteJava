package com.example.demo.Models;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;



@Entity
public class Route implements Serializable {
    @EmbeddedId
    public RouteKey routeKey;
    public int StopNumber;
   

    public Route() {
    }

    public Route(RouteKey routeKey, int StopNumber) {
        this.routeKey = routeKey;
        this.StopNumber = StopNumber;
    }


    public RouteKey getRouteKey() {
        return this.routeKey;
    }

    public void setRouteKey(RouteKey routeKey) {
        this.routeKey = routeKey;
    }

    public int getStopNumber() {
        return this.StopNumber;
    }

    public void setStopNumber(int StopNumber) {
        this.StopNumber = StopNumber;
    }

  

}

