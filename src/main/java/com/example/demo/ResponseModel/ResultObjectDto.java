package com.example.demo.ResponseModel;

import java.util.UUID;
import java.util.stream.Collectors;

import com.example.demo.Distance;
import com.vividsolutions.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;


public class ResultObjectDto{
    private UUID routeId;
    private List<HubObjectDto> data;
    private int routeDistance;
    private int totalDistance;

    public ResultObjectDto(UUID routeId, int routeDistance, int totalDistance) {
        this.routeId = routeId;
        this.routeDistance = routeDistance;
        this.totalDistance = totalDistance;
    }

    public ResultObjectDto(){
        
    }

    public UUID getRouteId() {
        return this.routeId;
    }

    public void setRouteId(UUID routeId) {
        this.routeId = routeId;
    }

    public List<HubObjectDto> getData() {
        return this.data;
    }

    public void setData(List<HubObjectDto> data) {
        this.data = data;
    }

    public int getRouteDistance() {
        return this.routeDistance;
    }

    public void setRouteDistance(int routeDistance) {
        this.routeDistance = routeDistance;
    }

    public int getTotalDistance() {

        List<Point> coordinatesArray = new ArrayList<>();
        for (HubObjectDto datum : data)
        {  
            Point hub = datum.getHubCoordinate();
            List<Point> deliverypoints = datum.getDeliveryAddresses().stream().map(place -> place.getOrderCoordinate()).collect(Collectors.toList());
            coordinatesArray.add(hub);
            coordinatesArray.addAll(deliverypoints);
        }
        for (int i = 0; i < coordinatesArray.size()-1; i++) {
            this.totalDistance += Distance.GetDistance(coordinatesArray.get(i), coordinatesArray.get(i+1));
        }
        coordinatesArray.clear();
        return this.totalDistance;
    }

    public void setTotalDistance(int totalDistance) {
        this.totalDistance = totalDistance;
    }

}
