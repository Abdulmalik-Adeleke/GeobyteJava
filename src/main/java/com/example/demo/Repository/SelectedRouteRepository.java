package com.example.demo.Repository;

import java.util.List;
import java.util.UUID;

import com.example.demo.Models.RouteKey;
import com.example.demo.Models.Selectedroute;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectedRouteRepository extends PagingAndSortingRepository<Selectedroute,RouteKey>{

    @Query(value="select * from selectedroute sr where sr.DestinationID = ?1 order by sr.stop_number", nativeQuery = true)
    List<Selectedroute> findByDestinationId(UUID ID);
}
