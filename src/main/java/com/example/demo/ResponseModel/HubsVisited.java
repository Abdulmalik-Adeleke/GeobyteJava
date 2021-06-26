package com.example.demo.ResponseModel;


import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vividsolutions.jts.geom.Point;


public class HubsVisited {
    
    @JsonIgnore
    private Point hubVisitedCoordinate;
    private double latitude;
    private double longitude;
    private int hubFee;


    public HubsVisited() {
    }

    public HubsVisited(Point hubVisitedCoordinate, int hubFee) {
        this.hubVisitedCoordinate = hubVisitedCoordinate;
        this.hubFee = hubFee;
    }

    public Point getHubVisitedCoordinate() {
        return this.hubVisitedCoordinate;
    }

    public void setHubVisitedCoordinate(Point hubVisitedCoordinate) {
        this.hubVisitedCoordinate = hubVisitedCoordinate;
    }

    public int getHubFee() {
        return this.hubFee;
    }

    public void setHubFee(int hubFee) {
        this.hubFee = hubFee;
    }


    public HubsVisited hubVisitedCoordinate(Point hubVisitedCoordinate) {
        setHubVisitedCoordinate(hubVisitedCoordinate);
        return this;
    }

	public double getLatitude() {
		this.latitude = hubVisitedCoordinate.getX();
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		this.longitude = hubVisitedCoordinate.getY();
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

    public HubsVisited hubFee(int hubFee) {
        setHubFee(hubFee);
        return this;
    }

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof HubsVisited)) {
			return false;
		}
		HubsVisited hubsVisited = (HubsVisited) o;
		return Objects.equals(hubVisitedCoordinate, hubsVisited.hubVisitedCoordinate) && latitude == hubsVisited.latitude && longitude == hubsVisited.longitude && hubFee == hubsVisited.hubFee;
	}

	@Override
	public int hashCode() {
		return Objects.hash(hubVisitedCoordinate, latitude, longitude, hubFee);
	}


	@Override
	public String toString() {
		return "{" +
			" hubVisitedCoordinate='" + getHubVisitedCoordinate() + "'" +
			", latitude='" + getLatitude() + "'" +
			", longitude='" + getLongitude() + "'" +
			", hubFee='" + getHubFee() + "'" +
			"}";
	}


}
