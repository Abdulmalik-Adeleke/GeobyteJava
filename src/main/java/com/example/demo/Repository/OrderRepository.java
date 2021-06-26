package com.example.demo.Repository;
import java.util.*;

import com.example.demo.Models.Orders;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface OrderRepository extends CrudRepository<Orders,Long> {

    @Query(value="SELECT DISTINCT id,user_name,order_coordinate,order_address FROM orders WHERE (ST_Y(order_coordinate) BETWEEN ?1 AND ?2) AND (ST_X(order_coordinate) BETWEEN ?3 AND ?4 AND delete_marked <> true)", nativeQuery = true)
    List<Optional<Orders>> findByOrderCoordinateBetween(double longMin, double longMax, double latMin, double latMax); 

    @Query(value="SELECT DISTINCT id,user_name,order_coordinate,order_address FROM orders WHERE (ST_Y(order_coordinate) BETWEEN ?1 AND ?2) AND (ST_X(order_coordinate) BETWEEN ?3 AND ?4) AND id NOT IN ?5 AND delete_marked <> true", nativeQuery = true)
    List<Optional<Orders>> findByOrderCoordinateBetween(double longMin, double longMax, double latMin, double latMax, List<Integer> currentOrders); 
    
}
