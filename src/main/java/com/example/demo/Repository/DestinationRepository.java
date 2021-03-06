package com.example.demo.Repository;

import java.util.List;
import java.util.UUID;

import com.example.demo.Models.Destination;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DestinationRepository extends CrudRepository<Destination,UUID> {

    @Query(value = "select * from destination where start_city = ?1 and end_city = ?2", nativeQuery = true)
    List<Destination> findByStartCityAndEndCity(String startCity,String endCity);

    @Query(value = "select DISTINCT start_city from destination", nativeQuery = true)
    List<String> findStartCity();

    @Query(value = "select DISTINCT end_city from destination where start_city = ?1", nativeQuery = true)
    List<String> findEndCity(String startCity);
}
