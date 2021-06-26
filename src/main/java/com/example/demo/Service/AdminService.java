package com.example.demo.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.example.demo.Dto.DestinationDto;
import com.example.demo.Dto.HubDto;
import com.example.demo.Models.Destination;
import com.example.demo.Models.Hub;
import com.example.demo.Models.Route;
import com.example.demo.Models.RouteKey;
import com.example.demo.Repository.DestinationRepository;
import com.example.demo.Repository.HubRepository;
import com.example.demo.Repository.RouteRepository;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {
    private final DestinationRepository destinationRepo;
    private final HubRepository hubRepo;
    private final RouteRepository routeRepo;

    @Autowired
    public AdminService(DestinationRepository destinationRepo,
    HubRepository hubRepo, 
    RouteRepository routeRepo)
    {
        this.destinationRepo = destinationRepo;
        this.hubRepo = hubRepo;
        this.routeRepo = routeRepo;
    }

    @Transactional(rollbackFor = { SQLException.class })
    public void insertDestination(DestinationDto destinationDto,List<String> routesobject) throws SQLException
    { 
        UUID id = UUID.randomUUID();
        Destination destionation = new Destination(id,
        destinationDto.getStartCity(),
        destinationDto.getEndCity(),
        destinationDto.getRouteDistance()
        );

        AtomicInteger num = new AtomicInteger(0);
        List<Route> routes = new ArrayList<>();
        routesobject.stream().forEachOrdered(city ->
        {
            RouteKey routeKey = new RouteKey(id,city);
            Route route = new Route(routeKey,num.getAndIncrement());
            routes.add(route);
        });

        destinationRepo.save(destionation);
        routeRepo.saveAll(routes);
    }
    
    public boolean insertHub(HubDto hubobject)
    {
        Optional<Hub> present = hubRepo.findById(hubobject.getCity());
        if (present.isEmpty())
        {
            GeometryFactory geom = new GeometryFactory();
            Point point =  geom.createPoint(new Coordinate(hubobject.getLatitude(),hubobject.getLatitude()));
            Hub hub = new Hub(hubobject.getCity(),hubobject.getAddress(),point,hubobject.getFee());
            hubRepo.save(hub);
            return true;
        }
        return false;
    }
    
    public List<HubDto> getHubs()
    {
        List<HubDto> hubs = new ArrayList<>();
        hubRepo.findAll().forEach(hub->{
            HubDto hubdto = new HubDto(hub.getCity(),
            hub.getAddress(),
            hub.getLocationCoordinates().getX(),
            hub.getLocationCoordinates().getY(),
            hub.getFee());

            hubs.add(hubdto);
        });
        return hubs;
    }
}
