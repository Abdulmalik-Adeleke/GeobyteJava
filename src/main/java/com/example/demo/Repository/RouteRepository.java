package com.example.demo.Repository;

import java.util.List;
import java.util.UUID;

import com.example.demo.Models.Route;
import com.example.demo.Models.RouteKey;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends CrudRepository<Route,RouteKey>
{

    @Query(value="select * from route where destiantionid = ?1", nativeQuery = true)
    List<Route> findByidDestinationID(UUID destinationid);
     
}
