package com.example.demo.Dto;

import java.util.Objects;

public class OrdersDto {
    private String userName;
    private double Latitude;
    private double Longitude;
    private String orderAddress;

    public OrdersDto(String userName, double Latitude, double Longitude, String orderAddress) {
        this.userName = userName;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.orderAddress = orderAddress;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getLatitude() {
        return this.Latitude;
    }

    public void setLatitude(double Latitude) {
        this.Latitude = Latitude;
    }

    public double getLongitude() {
        return this.Longitude;
    }

    public void setLongitude(double Longitude) {
        this.Longitude = Longitude;
    }

    public String getOrderAddress() {
        return this.orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof OrdersDto)) {
            return false;
        }
        OrdersDto ordersDto = (OrdersDto) o;
        return Objects.equals(userName, ordersDto.userName) && Latitude == ordersDto.Latitude && Longitude == ordersDto.Longitude && Objects.equals(orderAddress, ordersDto.orderAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, Latitude, Longitude, orderAddress);
    }

    @Override
    public String toString() {
        return "{" +
            " userName='" + getUserName() + "'" +
            ", Latitude='" + getLatitude() + "'" +
            ", Longitude='" + getLongitude() + "'" +
            ", orderAddress='" + getOrderAddress() + "'" +
            "}";
    }

}
