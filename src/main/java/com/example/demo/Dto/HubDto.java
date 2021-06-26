package com.example.demo.Dto;

import java.util.Objects;

public class HubDto {
    private String City;
    private String Address;
    private double Latitude;
    private double Longitude;
    private int Fee;


    public HubDto() {
    }

    public HubDto(String City, String Address, double Latitude, double Longitude, int Fee) {
        this.City = City;
        this.Address = Address;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
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
        if (!(o instanceof HubDto)) {
            return false;
        }
        HubDto hubDto = (HubDto) o;
        return Objects.equals(City, hubDto.City) && Objects.equals(Address, hubDto.Address) && Latitude == hubDto.Latitude && Longitude == hubDto.Longitude && Fee == hubDto.Fee;
    }

    @Override
    public int hashCode() {
        return Objects.hash(City, Address, Latitude, Longitude, Fee);
    }

    @Override
    public String toString() {
        return "{" +
            " City='" + getCity() + "'" +
            ", Address='" + getAddress() + "'" +
            ", Latitude='" + getLatitude() + "'" +
            ", Longitude='" + getLongitude() + "'" +
            ", Fee='" + getFee() + "'" +
            "}";
    }

}
