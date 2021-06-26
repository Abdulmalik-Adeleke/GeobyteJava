package com.example.demo.Models;

import java.util.UUID;

public class DestinationInfo {
    
    private UUID DestinationID;
    private int DestinationDistance;
    

    public DestinationInfo() {
    }

    public DestinationInfo(UUID DestinationID, int DestinationDistance) {
        this.DestinationID = DestinationID;
        this.DestinationDistance = DestinationDistance;
    }

    public UUID getDestinationID() {
        return this.DestinationID;
    }

    public void setDestinationID(UUID DestinationID) {
        this.DestinationID = DestinationID;
    }

    public int getDestinationDistance() {
        return this.DestinationDistance;
    }

    public void setDestinationDistance(int DestinationDistance) {
        this.DestinationDistance = DestinationDistance;
    }

}

    