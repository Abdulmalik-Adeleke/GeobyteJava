package com.example.demo.ResponseModel;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vividsolutions.jts.geom.Point;


public class OrderObjectDto {
    private Long id;
    private String userName;
    @JsonIgnore
    private Point orderCoordinate;
    private double latitude;
    private double longitude;
    private String orderAddress;
    private int distanceFromOrigin;
    private long fee;
    private Set<HubsVisited> hubsVisitedPlusDestination;

    public OrderObjectDto() {
    }


    public OrderObjectDto(Long id, String userName, Point orderCoordinate, String orderAddress, int distanceFromOrigin, Set<HubsVisited> hubsVisitedPlusDestination) {
        this.id = id;
        this.userName = userName;
        this.orderCoordinate = orderCoordinate;
        this.orderAddress = orderAddress;
        this.distanceFromOrigin = distanceFromOrigin;
        this.hubsVisitedPlusDestination = hubsVisitedPlusDestination;
    }

    public OrderObjectDto(Long id, String userName,String orderAddress,Point orderCoordinate) {
        this.id = id;
        this.userName = userName;
        this.orderAddress = orderAddress;
        this.orderCoordinate = orderCoordinate;
    }

    public long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Point getOrderCoordinate() {
        return this.orderCoordinate;
    }

    public void setOrderCoordinate(Point orderCoordinate) {
        this.orderCoordinate = orderCoordinate;
    }

    public long getFee() {
        this.fee = distanceFromOrigin;
        for (HubsVisited hubsVisited : hubsVisitedPlusDestination) {
            this.fee += hubsVisited.getHubFee();
        }
        return this.fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }

    public String getOrderAddress() {
        return this.orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public int getDistanceFromOrigin() {
        return this.distanceFromOrigin;
    }

    public void setDistanceFromOrigin(int distanceFromOrigin) {
        this.distanceFromOrigin = distanceFromOrigin;
    }

    public Set<HubsVisited> getHubsVisitedPlusDestination() {
        return this.hubsVisitedPlusDestination;
    }

    public void setHubsVisitedPlusDestination(Set<HubsVisited> hubsVisitedPlusDestination) {
        this.hubsVisitedPlusDestination = hubsVisitedPlusDestination;
    }

    public double getLatitude() {
        this.latitude = orderCoordinate.getX();
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        this.longitude = orderCoordinate.getY();
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
