package com.example.demo.Dto;

import java.util.List;

public class DestinationRouteDto {

    private DestinationDto destinationdto;
    private List<String> routes;


    public DestinationDto getDestinationdto() {
        return this.destinationdto;
    }

    public void setDestinationdto(DestinationDto destinationdto) {
        this.destinationdto = destinationdto;
    }

    public List<String> getRoutes() {
        return this.routes;
    }

    public void setRoutes(List<String> routes) {
        this.routes = routes;
    }

    
}
