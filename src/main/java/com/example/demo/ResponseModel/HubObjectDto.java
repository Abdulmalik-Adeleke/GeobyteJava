package com.example.demo.ResponseModel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vividsolutions.jts.geom.Point;

public class HubObjectDto {

    @JsonIgnore
    private Point hubCoordinate;
    private double latitude;
    private double longitude;
    private String hubAddress;
    private int hubFee;
    private List<OrderObjectDto> deliveryAddresses;



    public HubObjectDto(Point hubCoordinate, String hubAddress, int hubFee) {
        this.hubCoordinate = hubCoordinate;
        this.hubAddress = hubAddress;
        this.hubFee = hubFee;
    }


    public HubObjectDto() {
    }

    public Point getHubCoordinate() {
        return this.hubCoordinate;
    }

    public void setHubCoordinate(Point hubCoordinate) {
        this.hubCoordinate = hubCoordinate;
        latitude = this.hubCoordinate.getX();
        longitude = this.hubCoordinate.getY();
    }

    public String getHubAddress() {
        return this.hubAddress;
    }

    public void setHubAddress(String hubAddress) {
        this.hubAddress = hubAddress;
    }

    public int getHubFee() {
        return this.hubFee;
    }

    public void setHubFee(int hubFee) {
        this.hubFee = hubFee;
    }

    public List<OrderObjectDto> getDeliveryAddresses() {
        return this.deliveryAddresses;
    }

    public void setDeliveryAddresses(List<OrderObjectDto> deliveryAddresses) {
        this.deliveryAddresses = deliveryAddresses;
    }


    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


}
