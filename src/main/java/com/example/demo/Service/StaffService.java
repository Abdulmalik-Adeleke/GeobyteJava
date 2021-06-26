package com.example.demo.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.example.demo.Distance;
import com.example.demo.Dto.HubDto;
import com.example.demo.Models.DestinationInfo;
import com.example.demo.Models.Orders;
import com.example.demo.Models.Selectedroute;
import com.example.demo.Repository.DestinationRepository;
import com.example.demo.Repository.HubRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.SelectedRouteRepository;
import com.example.demo.ResponseModel.HubObjectDto;
import com.example.demo.ResponseModel.HubsVisited;
import com.example.demo.ResponseModel.OrderObjectDto;
import com.example.demo.ResponseModel.ResultObjectDto;
import com.vividsolutions.jts.geom.Point;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService {
   private final SelectedRouteRepository selectedrouterepo;
   private final DestinationRepository destinationrepo;
   private final OrderRepository orderrepo;
   private final HubRepository hubrepo;

    @Autowired
    public StaffService(SelectedRouteRepository selectedrouterepo,
     DestinationRepository destinationrepo,
     OrderRepository orderrepo,
     HubRepository hubrepo) {
        this.selectedrouterepo = selectedrouterepo;
        this.destinationrepo = destinationrepo;
        this.orderrepo = orderrepo;
        this.hubrepo = hubrepo;
    }

    public List<HubDto> getHubs()
    {
        List<HubDto> hubs = new ArrayList<>();
        hubrepo.findAll().forEach(hub->{
            HubDto hubdto = new HubDto(hub.getCity(),
            hub.getAddress(),
            hub.getLocationCoordinates().getX(),
            hub.getLocationCoordinates().getY(),
            hub.getFee());

            hubs.add(hubdto);
        });
        return hubs;
    }

    public List<DestinationInfo> getDestinations(String start, String end)
    {
        var result = destinationrepo.findByStartCityAndEndCity(start, end);
        
        List<DestinationInfo> list = new ArrayList<DestinationInfo>();

        result.stream().forEach(destination -> {
            DestinationInfo info = new DestinationInfo(destination.getDestinationID(),destination.getRouteDistance());
            list.add(info);
        });

        return list;
        
    }

    public ResultObjectDto getResult(DestinationInfo destinationInfo, double radiusInKm)
    {
        ResultObjectDto result = new ResultObjectDto();
        List<HubObjectDto> map = new ArrayList<HubObjectDto>();
       
        //miles required for search in formula
        double radiusInMiles  = radiusInKm * 0.621; 

        var Distance = destinationInfo.getDestinationDistance();

        List<Selectedroute> route = selectedrouterepo.findByDestinationId(destinationInfo.getDestinationID()); 

        route.stream().forEachOrdered(hub ->{
            List<OrderObjectDto> orderdto = new ArrayList<>();

            HubObjectDto hubobject = new HubObjectDto();
            double Latitude = hub.getLocation_coordinates().getX();
            double Longitude = hub.getLocation_coordinates().getY();;
            double lonMin = Longitude - radiusInMiles / Math.abs(Math.acos(Math.toRadians(Latitude)) * 69);
            double lonMax = Longitude + radiusInMiles / Math.abs(Math.acos(Math.toRadians(Latitude)) * 69);
            double latMin = Latitude - (radiusInMiles / 69);
            double latMax = Latitude + (radiusInMiles / 69);    
            
            System.out.print("Hub Coords: "+hub.getLocation_coordinates());
            System.out.print("Longitude Minimum "+lonMin);
            System.out.print("Longitude Maximim "+lonMax);
            System.out.print("Latitude Minimum "+latMin);
            System.out.print("Latitude Maximum "+latMax);
            List<Optional<Orders>> orderlist = orderrepo.findByOrderCoordinateBetween(lonMin, lonMax, latMin, latMax);
            
            for (Optional<Orders> o : orderlist) {
             
                o.ifPresent(x->{
                    OrderObjectDto order = new OrderObjectDto(x.getId(),
                    x.getUserName(),
                    x.getOrderAddress(),
                    x.getOrderCoordinate());

                    orderdto.add(order);
                });
                 
            }    
            hubobject.setDeliveryAddresses(orderdto);
            hubobject.setHubAddress(hub.getAddress());
            hubobject.setHubCoordinate(hub.getLocation_coordinates());
            hubobject.setHubFee(hub.getFee());  
            map.add(hubobject);    
        
            
        });

        result.setRouteDistance(Distance);
        result.setData(map);
        result.setRouteId(destinationInfo.getDestinationID());

        // haversines principle      
        return  set_OrderDistanceFromOrigin_HubsVisitedPlusDestination(result);

    }

    public List<OrderObjectDto> getMoreDeliveryLocations(UUID destinationid, List<Integer> idsToIgnore,double radiusInKm)
    {
        double radiusInMiles = radiusInKm * 0.621; 

        List<Selectedroute> route = selectedrouterepo.findByDestinationId(destinationid); 

        Point origin = route.get(0).getLocation_coordinates();
        HubsVisited destination = new HubsVisited(route.get(route.size()-1).getLocation_coordinates(),route.get(route.size()-1).getFee());
        List<HubsVisited> hubsVisited = new ArrayList<>();
        hubsVisited.add(destination);

        boolean flag = false;

        List<OrderObjectDto> orderlist = new ArrayList<>();

        /*
        *  Cannot use stream() as 
        *  "lambda's capture values not variables" -- line 151
        */
        for (Selectedroute hub : route) 
        {
            double Latitude = hub.getLocation_coordinates().getX();
            double Longitude = hub.getLocation_coordinates().getY();;
            double lonMin = Longitude - radiusInMiles / Math.abs(Math.acos(Math.toRadians(Latitude)) * 69);
            double lonMax = Longitude + radiusInMiles / Math.abs(Math.acos(Math.toRadians(Latitude)) * 69);
            double latMin = Latitude - (radiusInMiles / 69);
            double latMax = Latitude + (radiusInMiles / 69); 
            
            System.out.print("Hub Coords: "+hub.getLocation_coordinates());
            System.out.print("Longitude Minimum "+lonMin);
            System.out.print("Longitude Maximim "+lonMax);
            System.out.print("Latitude Minimum "+latMin);
            System.out.print("Latitude Maximum "+latMax);

            if(flag){
                hubsVisited.add(new HubsVisited(
                hub.getLocation_coordinates(),
                hub.getFee()));
            }
            flag = true;
            
            var orders = orderrepo.findByOrderCoordinateBetween(lonMin, lonMax, latMin, latMax,idsToIgnore);
           
            for (Optional<Orders> o : orders) {
             
                o.ifPresent(x->{
                    OrderObjectDto order = new OrderObjectDto(
                    x.getId(),
                    x.getUserName(),
                    x.getOrderCoordinate(),
                    x.getOrderAddress(),
                    Distance.GetDistance(origin, x.getOrderCoordinate()),
                    Set.copyOf(hubsVisited));
                   
                    orderlist.add(order);
                });
            }
        }    

        return orderlist;
        
    }

    static ResultObjectDto set_OrderDistanceFromOrigin_HubsVisitedPlusDestination(ResultObjectDto result)
    {
        List<HubObjectDto> arrayObject = result.getData();

        ResultObjectDto resultObject = new ResultObjectDto(
        result.getRouteId(),
        result.getRouteDistance(),
        result.getTotalDistance()
        );

        int destinationIdx = arrayObject.size()-1;
        HubsVisited origin = new HubsVisited(arrayObject.get(0).getHubCoordinate(),arrayObject.get(0).getHubFee());
        HubsVisited destination = new HubsVisited(arrayObject.get(destinationIdx).getHubCoordinate(),arrayObject.get(destinationIdx).getHubFee());

        List<HubObjectDto> hubs = new ArrayList<>();
     
        Set<HubsVisited> hubsVisited = new HashSet<HubsVisited>();
        List<HubsVisited> hubsVisiteddto = new ArrayList<>();
        
        boolean flag = false;

        int skipduplicate = arrayObject.size()-1;
        int skip = 0;
        
        for (HubObjectDto hub : arrayObject) 
        {
            List<OrderObjectDto> orders = new ArrayList<>();
            hub.setHubAddress(hub.getHubAddress());
            hub.setHubCoordinate(hub.getHubCoordinate());
            hub.setHubFee(hub.getHubFee());
                
            if(flag)
            {
            hubsVisited.add(new HubsVisited(
            hub.getHubCoordinate(),
            hub.getHubFee()));
            }
            flag = true;
            for (OrderObjectDto order : hub.getDeliveryAddresses()) 
            {    
                order.setDistanceFromOrigin( Distance.GetDistance(origin.getHubVisitedCoordinate(),order.getOrderCoordinate()));
                
                if(skip != skipduplicate)
                {
                    hubsVisiteddto.addAll(hubsVisited);
                    hubsVisiteddto.add(destination);
                    skip++;
                }
                else
                {
                    hubsVisiteddto.addAll(hubsVisited);
                }

                OrderObjectDto orderdto = new OrderObjectDto();
                orderdto.setId(order.getId());
                orderdto.setUserName(order.getUserName());
                orderdto.setOrderCoordinate(order.getOrderCoordinate());
                orderdto.setOrderAddress(order.getOrderAddress());
                orderdto.setDistanceFromOrigin(order.getDistanceFromOrigin());
                orderdto.setHubsVisitedPlusDestination( Set.copyOf(hubsVisiteddto));
                orders.add(orderdto);  
                
                hub.setDeliveryAddresses(orders);
            }

            hubs.add(hub);
        
        }
        resultObject.setData(hubs);
        return resultObject;
    }


}