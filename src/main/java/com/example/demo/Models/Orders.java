package com.example.demo.Models;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.vividsolutions.jts.geom.Point;


@Entity
@Table
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private String userName;
    private Point orderCoordinate;
    private String orderAddress;
    private Boolean deleteMarked;

    public long getId() {
        return this.Id;
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

    public String getOrderAddress() {
        return this.orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public Boolean getDeleteMarked() {
        return this.deleteMarked;
    }

    public void setDeleteMarked(Boolean deleteMarked) {
        this.deleteMarked = deleteMarked;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Orders)) {
            return false;
        }
        Orders order = (Orders) o;
        return Id == order.Id && Objects.equals(userName, order.userName) && Objects.equals(orderCoordinate, order.orderCoordinate) && Objects.equals(orderAddress, order.orderAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, userName, orderCoordinate, orderAddress);
    }


}
